package mulla.compilador;

import gals.SemanticError;
import gals.Token;
import mulla.compilador.somador.Somador;
import mulla.compilador.somador.SomadorFactory;

public class SemanticoMula {

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
			a = CompiladorTeste.pilha.pop();
			b = CompiladorTeste.pilha.pop();
			Somador<?> somador = SomadorFactory.criaSomador(a, b);
			Object soma = somador.soma(a, b);
			CompiladorTeste.pilha.push( soma );
			break;
		default:
			break;
		}
    }	
	
}
