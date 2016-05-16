package gals;

import mulla.compilador.SemanticoMula;

public class Semantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticError
    {
    	try {
			SemanticoMula.executeAction(action, token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
}
