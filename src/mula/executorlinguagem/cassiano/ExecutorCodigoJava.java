package mula.executorlinguagem.cassiano;

import java.util.logging.Level;
import java.util.logging.Logger;

import bsh.EvalError;
import bsh.Interpreter;

public class ExecutorCodigoJava {

    private final Interpreter interpretador;
    private Object retorno;

    public ExecutorCodigoJava() {
        interpretador = new Interpreter();
    }

    public void executar(String codigo) {
        try {
            retorno = interpretador.eval(codigo);
        } catch (EvalError ex) {
            Logger.getLogger(ExecutorCodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSintaxe(ex);
        }
    }

    public <T> T getResultado(String nomeVariavel) {
        try {
            return (T) interpretador.get(nomeVariavel);
        } catch (EvalError ex) {
            Logger.getLogger(ExecutorCodigoJava.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSintaxe(ex);
        }
    }

    public Object getResultado() {
        return retorno;
    }

}
