package mulla.compilador;

import gals.Token;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import mulla.compilador.somador.Somador;
import mulla.compilador.somador.SomadorFactory;
import mulla.compilador.somador.impl.StringUtil;

public class SemanticoMula {

	private static int intA;
	private static int intB;
	private static String nomeVariavel;
	private static Variavel var;
	private static Object a, b;

	public static void executeAction(int action, Token token) throws Exception {
		switch (action) {
		case 1:
			push(Integer.parseInt(token.getLexeme()));
			break;
		case 2:
			push(token.getLexeme());
			break;
		case 3:
			b = pop();
			a = pop();
			Somador<?> somador = SomadorFactory.criaSomador(a, b);
			Object soma = somador.soma(a, b);
			push(soma);
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
			//String linguagem = pop().toString();
			break;
		default:
			break;
		}
	}

	private static void escreveConsole() {
		Object pop = pop();
		String out = getValorDiretoOuValorDaVariavel(pop);
		System.out.print(out);
	}

	private static void extraiInteirosDaPilha() {
		Object b = pop();
		Object a = pop();
		intA = Integer.parseInt(a.toString());
		intB = Integer.parseInt(b.toString());
	}

	private static void declaraVariavel(Token token) {
		nomeVariavel = token.getLexeme();
		if (CompiladorTeste.variaveis.containsKey(nomeVariavel)) {
			var = CompiladorTeste.variaveis.get(nomeVariavel);
		} else {
			var = new Variavel(nomeVariavel);
		}
		put(nomeVariavel, var);
		push(nomeVariavel);
	}

	private static void executaAtribuicao() {
		a = pop();
		nomeVariavel = pop().toString();
		getVariavel(nomeVariavel).setValor(a);
	}

	private static void empilhaValorVariavel(Token token) {
		Variavel variavel = getVariavel(token.getLexeme());
		CompiladorTeste.pilha.push(variavel.getValor());
	}

	private static void leArquivo() throws IOException {
		String nomeArquivo = getValorDiretoOuValorDaVariavel(pop());
		nomeVariavel = pop().toString();
		String conteudoArquivo = leConteudoArquivo(nomeArquivo);
		getVariavel(nomeVariavel).setValor(conteudoArquivo);
	}

	private static String leConteudoArquivo(String nomeArquivo)
			throws IOException {
		nomeArquivo = StringUtil.removeAspas(nomeArquivo);
		List<String> lines = Files.readAllLines(Paths.get(nomeArquivo));
		StringBuilder conteudoArquivo = new StringBuilder();
		lines.forEach(conteudoArquivo::append);
		return conteudoArquivo.toString();
	}

	private static void escreveArquivo() throws IOException {
		String nomeArquivo = StringUtil
				.removeAspas(getValorDiretoOuValorDaVariavel(pop()));
		String conteudoEscrever = StringUtil
				.removeAspas(getValorDiretoOuValorDaVariavel(pop()));
		Files.write(Paths.get(nomeArquivo), Arrays.asList(conteudoEscrever),
				Charset.forName("UTF-8"));
	}

	private static Object pop() {
		return CompiladorTeste.pilha.pop();
	}

	private static void put(String nomeVariavel2, Variavel var2) {
		CompiladorTeste.variaveis.put(nomeVariavel,var);
	}

	private static Object push(Object item) {
		return CompiladorTeste.pilha.push(item);
	}

	private static Variavel getVariavel(String lexeme) {
		return CompiladorTeste.variaveis.get(lexeme);
	}
	
	private static String getValorDiretoOuValorDaVariavel(Object valorOuVariavel) {
		if (valorOuVariavel instanceof Variavel)
			return ((Variavel) valorOuVariavel).getValor().toString();
		else
			return valorOuVariavel.toString();
	}


}
