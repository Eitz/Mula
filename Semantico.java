package gals;

import compiladorteste.CompiladorTeste;

public class Semantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticError
    {
        //System.out.println("Ação #"+action+", Token: "+token);
        Double a, b;
        switch(action){
            case 3:
                CompiladorTeste.pilha.push(
                        Double.parseDouble(token.getLexeme()));
                break;
            case 1:
                a = CompiladorTeste.pilha.pop();
                b = CompiladorTeste.pilha.pop();
                CompiladorTeste.pilha.push(a+b);
                break;
            case 2:
                a = CompiladorTeste.pilha.pop();
                b = CompiladorTeste.pilha.pop();
                CompiladorTeste.pilha.push(a*b);
                break;
        }
    }	
}
