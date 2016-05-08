package interpretador;

import bsh.EvalError;
import bsh.Interpreter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutorCodigoJava {

    private final Interpreter interpretador;

    public ExecutorCodigoJava() {
        interpretador = new Interpreter();
    }
    
    public void executar(String codigo) {
        try {
            interpretador.eval(codigo);
        } catch (EvalError ex) {
            Logger.getLogger(ExecutorCodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSintaxe(ex);
        }
    }

    public<T> T getResultado(String nomeVariavel) {
        try {
            return (T) interpretador.get(nomeVariavel);
        } catch (EvalError ex) {
            Logger.getLogger(ExecutorCodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSintaxe(ex);
        }
    }

}
