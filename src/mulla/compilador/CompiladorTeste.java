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
import java.util.HashSet;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompiladorTeste {
    public static Stack<Object> pilha = new Stack<>();
	public static HashMap<String, Variavel> variaveis = new HashMap<>();

    public static void main(String[] args) {
        try {
            Lexico lex = new Lexico("a = 2 * (3 + 5);");
            Sintatico sin = new Sintatico();
            Semantico sem = new Semantico();
            sin.parse(lex, sem);
            System.out.println("Resultado: "+pilha.pop());
        } catch (LexicalError ex) {
            Logger.getLogger(CompiladorTeste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SyntaticError ex) {
            Logger.getLogger(CompiladorTeste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SemanticError ex) {
            Logger.getLogger(CompiladorTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
