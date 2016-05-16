/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mulla.compilador;

import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Semantico;
import gals.Sintatico;
import gals.SyntaticError;

import java.util.HashMap;
import java.util.Stack;

public class CompiladorTeste {
    public static Stack<Object> pilha = new Stack<>();
	public static HashMap<String, Variavel> variaveis = new HashMap<>();

	public static void test(String mulaCode){
		Lexico lex = new Lexico(mulaCode);
		Sintatico sin = new Sintatico();
        Semantico sem = new Semantico();
        try {
			sin.parse(lex, sem);
	        //variaveis.values().forEach(System.out::println);
		} catch (LexicalError | SyntaticError | SemanticError e) {
			throw new RuntimeException(e);
		}
	}
	
    public static void main(String[] args) {
        CompiladorTeste.test(
            		  "a = 2 * (3 + 5);"
            		+ "b = 14 * (10 / 5);"
            		+ "c = a + b;"
            		+ "c = c + 6;"
            	);
    }
    
}
