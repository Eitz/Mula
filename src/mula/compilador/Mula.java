package mula.compilador;

import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Sintatico;
import gals.SyntaticError;

public class Mula {

	SemanticoMula sem = new SemanticoMula();
	
	public void execute(String mulaCode) {
		Lexico lex = new Lexico(mulaCode);
		Sintatico sin = new Sintatico();
		try {
			sin.parse(lex, sem);
		} catch (LexicalError | SyntaticError | SemanticError e) {
			throw new RuntimeException(e);
		}
	}

	public Object getValorDe(String key) {
		return sem.getValorDe(key);
	}

}
