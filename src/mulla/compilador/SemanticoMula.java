package mulla.compilador;

import gals.SemanticError;
import gals.Token;
import mulla.compilador.somador.Somador;
import mulla.compilador.somador.SomadorFactory;

public class SemanticoMula {

	private static int intA;
	private static int intB;

	public static void executeAction(int action, Token token)	throws SemanticError
    {
    	Object a, b;
        switch (action) {
		case 1:
			CompiladorTeste.pilha.push( Integer.parseInt(token.getLexeme()) );
			break;
		case 2: 
			CompiladorTeste.pilha.push( token.getLexeme() );
			break;
		case 3:
			b = CompiladorTeste.pilha.pop();
			a = CompiladorTeste.pilha.pop();
			Somador<?> somador = SomadorFactory.criaSomador(a, b);
			Object soma = somador.soma(a, b);
			CompiladorTeste.pilha.push( soma );
			break;
		case 4:
			extraiInteirosDaPilha();
			CompiladorTeste.pilha.push( intA-intB );
			break;
		case 5:
			extraiInteirosDaPilha();
			CompiladorTeste.pilha.push( intA*intB );
			break;
		case 6:
			extraiInteirosDaPilha();
			CompiladorTeste.pilha.push( intA/intB );
			break;
		case 7: // declaracao
			String nomeVar = token.getLexeme();
			Variavel var = new Variavel(nomeVar);
			CompiladorTeste.variaveis.put(nomeVar, var);
			break;
		case 8: // atribuição
			
		default:
			break;
		}
    }

	private static void extraiInteirosDaPilha() {
		Object a;
		Object b;
		b = CompiladorTeste.pilha.pop();
		a = CompiladorTeste.pilha.pop();
		intA = Integer.parseInt(a.toString());
		intB = Integer.parseInt(b.toString());
	}	
	
}
