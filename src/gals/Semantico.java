package gals;

import mulla.compilador.SemanticoMula;

public class Semantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticError
    {
        SemanticoMula.executeAction(action, token);
    }	
}
