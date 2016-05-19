package mula.compilador;

import gals.Constants;
import gals.SemanticError;
import gals.Semantico;
import gals.Token;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import mula.compilador.somador.Somador;
import mula.compilador.somador.SomadorFactory;
import mula.compilador.somador.impl.StringUtil;
import mula.executorlinguagem.ExecutorLinguagem;
import mula.executorlinguagem.ExecutorLinguagemFactory;

public class SemanticoMula extends Semantico implements Constants {

	private static int intA;
	private static int intB;
	private static String nomeVariavel;
	private static Variavel var;

	public static Stack<Object> pilha = new Stack<>();
	public static HashMap<String, Variavel> variaveis = new HashMap<>();

	public void executeAction(int action, Token token) throws SemanticError {
		switch (action) {
		case 1:
			push(Integer.parseInt(token.getLexeme()));
			break;
		case 2:
			push(token.getLexeme());
			break;
		case 3:
			Object b = pop();
			Object a = pop();
			push(soma(b, a));
			break;
		case 4:
			extraiInteirosDaPilha();
			push(intA - intB);
			break;
		case 5:
			extraiInteirosDaPilha();
			push(intA * intB);
			break;
		case 6:
			extraiInteirosDaPilha();
			push(intA / intB);
			break;
		case 7:
			declaraVariavel(token);
			break;
		case 8:
			executaAtribuicao();
			break;
		case 9:
			empilhaValorVariavel(token);
			break;
		case 10:
			leArquivo();
			break;
		case 11:
			escreveArquivo();
			break;
		case 12:
			escreveConsole();
			break;
		case 13:
			run(token);
			break;
		default:
			break;
		}
	}

	private static Object soma(Object b, Object a) {
		Somador<?> somador = SomadorFactory.criaSomador(a, b);
		Object soma = somador.soma(a, b);
		return soma;
	}

	private static void escreveConsole() {
		String valorPrintar = popValue();
		System.out.println(valorPrintar);
	}

	private static void extraiInteirosDaPilha() {
		Object b = pop();
		Object a = pop();
		intA = Integer.parseInt(a.toString());
		intB = Integer.parseInt(b.toString());
	}

	private static void declaraVariavel(Token token) {
		nomeVariavel = token.getLexeme();
		if (variaveis.containsKey(nomeVariavel)) {
			var = variaveis.get(nomeVariavel);
		} else {
			var = new Variavel(nomeVariavel);
		}
		put(nomeVariavel, var);
		push(nomeVariavel);
	}

	private static void executaAtribuicao() {
		Object valorExpressao = pop();
		String nomeVariavel = pop().toString();
		getVariavel(nomeVariavel).setValor(valorExpressao);
	}

	private static void empilhaValorVariavel(Token token) {
		Variavel variavel = getVariavel(token.getLexeme());
		pilha.push(variavel.getValor());
	}

	private static void leArquivo() {
		String nomeArquivo = popValue();
		String nomeVariavel = pop().toString();
		String conteudoArquivo = leConteudoArquivo(nomeArquivo);
		getVariavel(nomeVariavel).setValor(conteudoArquivo);
	}

	private static String leConteudoArquivo(String nomeArquivo) {
		try {
			return leConteudo(nomeArquivo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String leConteudo(String nomeArquivo) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(nomeArquivo));
		StringBuilder conteudoArquivo = new StringBuilder();
		lines.forEach(conteudoArquivo::append);
		return conteudoArquivo.toString();
	}

	private static void escreveArquivo() {
		try {
			tentaEscreveArquivo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void tentaEscreveArquivo() throws IOException {
		String nomeArquivo = popValue();
		String conteudoEscrever = popValue();
		Files.write(Paths.get(nomeArquivo), Arrays.asList(conteudoEscrever),
				Charset.forName("UTF-8"));
	}

	private static void run(Token token) {
		String linguagem = popValue();
		ExecutorLinguagem executor = ExecutorLinguagemFactory
				.criaExecutor(linguagem);
		String codigoFonte = corrigeCodigo(token.getLexeme());
		executor.executa(codigoFonte);
		if (executor.temRetorno()) {
			push(executor.getRetorno());
		}
	}

	private static String corrigeCodigo(String codigoFonte) {
		/*
		 * Ordena os nomes das variáveis, colocando 
		 * os nomes maiores primeiro. Isso é feito para que
		 * no momento do replace, sejam substituidas primeiros
		 * os nomes maiores, para que não ocorra o problema 
		 * de substituir o valor de 'var' em %=var2, por exemplo.
		 */
		List<String> nomesVariaveis = ordenaNomesVariaveis();
		codigoFonte = executaReplace(codigoFonte, nomesVariaveis);
		codigoFonte = removeMarcadoresDeInicioEhFim(codigoFonte);
		return codigoFonte;
	}

	private static List<String> ordenaNomesVariaveis() {
		Comparator<String> comparator = (sa,sb) -> {
			if( sa.length() > sb.length() ){
				return -1; 
			} else if(sa.length() > sb.length()){
				return 1;
			} else {
				return sa.compareTo(sb);
			}
		};
		
		List<String> nomesVariaveis = new ArrayList<>(variaveis.keySet());
		Collections.sort(nomesVariaveis, comparator);
		return nomesVariaveis;
	}

	private static String executaReplace(String codigoFonte,
			List<String> nomesVariaveis) {
		for (String nomeVariavel : nomesVariaveis) {
			Variavel v = variaveis.get(nomeVariavel);
			String valorVariavel = v.getValor().toString();
			codigoFonte = codigoFonte.replaceAll("%=" + nomeVariavel,
					valorVariavel);
		}
		return codigoFonte;
	}

	private static String removeMarcadoresDeInicioEhFim(String codigoFonte) {
		codigoFonte = codigoFonte.replaceAll("<%|%>", "");
		return codigoFonte;
	}

	private static Object pop() {
		return pilha.pop();
	}

	private static String popValue() {
		return StringUtil.removeAspas(getValorDiretoOuValorDaVariavel(pop()));
	}

	private static void put(String nomeVariavel2, Variavel var2) {
		variaveis.put(nomeVariavel, var);
	}

	private static Object push(Object item) {
		return pilha.push(item);
	}

	private static Variavel getVariavel(String lexeme) {
		return variaveis.get(lexeme);
	}

	private static String getValorDiretoOuValorDaVariavel(Object valorOuVariavel) {
		if (valorOuVariavel instanceof Variavel)
			return ((Variavel) valorOuVariavel).getValor().toString();
		else
			return valorOuVariavel.toString();
	}

	public Object getValorDe(String key) {
		return variaveis.get(key).getValor();
	}

}
