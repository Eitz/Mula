package compiladorcodigojava;

import interpretador.ExecutorCodigoJava;
import bsh.EvalError;
import bsh.Interpreter;
import interpretador.Mula;
import java.io.IOException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorCodigoJavaTest {

    public ExecutorCodigoJavaTest() {
    }

    @Test
    public void testSomeMethod() throws EvalError, IOException {
        Interpreter i = new Interpreter();  // Construct an interpreter
        i.set("foo", 5);                    // Set variables
        i.set("date", new Date());
        Date date = (Date) i.get("date");    // retrieve a variable
        // Eval a statement and get the result
        i.eval("bar = foo*10");
        System.out.println(i.get("bar"));
        // Source an external script file
        //i.source("somefile.bsh");
    }

    @Test
    public void rodarMetodoSomaERetornarResultado() {
        ExecutorCodigoJava executor = new ExecutorCodigoJava();
        String codigo = "int soma = 1 + 1;";
        executor.executar(codigo);
        int resultado = executor.getResultado("soma");
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void rodarMetodoSomaEMetodoSubtracaoERetornarResultados() {
        ExecutorCodigoJava executor = new ExecutorCodigoJava();
        String codigo = "int soma = 1 + 1; int subtracao = 4 - 1;";
        executor.executar(codigo);
        int soma = executor.getResultado("soma");
        int subtracao = executor.getResultado("subtracao");
        Assert.assertEquals(2, soma);
        Assert.assertEquals(3, subtracao);
    }

    @Test
    public void testarDeclaracaoDeMetodo() {
        ExecutorCodigoJava executor = new ExecutorCodigoJava();
        String codigo
                = " int somar(int a, int b){ return a + b; } "
                + " int c = somar(2,2); ";
        executor.executar(codigo);
        int soma = executor.getResultado("c");
        Assert.assertEquals(4, soma);
    }

    @Test
    public void testarSaidaNoConsole() {
        ExecutorCodigoJava executor = new ExecutorCodigoJava();
        String codigo = "System.out.println(\"Teste\")";
        executor.executar(codigo);
    }

    @Test
    public void testarPassandoArrayDeParametrosNoFormatoDaNossaLinguagem() {
        ExecutorCodigoJava executor = new ExecutorCodigoJava();
        executor.setParametros("{a:3, b:3}");
        executor.executar("int soma = Integer.parseInt(Mula.getParam(\"a\")) + Integer.parseInt(Mula.getParam(\"b\"));");
        int resultado = executor.getResultado("soma");
        Assert.assertEquals(6, resultado);
    }
}
