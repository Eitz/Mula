package mula.compilador;

import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Semantico;
import gals.Sintatico;
import gals.SyntaticError;

import java.util.HashMap;
import java.util.Stack;

public class Mula {
	public Stack<Object> pilha = new Stack<>();
	public HashMap<String, Variavel> variaveis = new HashMap<>();
	
	public void execute(String mulaCode) {
		Lexico lex = new Lexico(mulaCode);
		Sintatico sin = new Sintatico();
		Semantico sem = new Semantico();
		try {
			sin.parse(lex, sem);
		} catch (LexicalError | SyntaticError | SemanticError e) {
			throw new RuntimeException(e);
		}
	}

}
