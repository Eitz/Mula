package mula.executorlinguagem;

import mula.executorlinguagem.java.ExecutorJava;
import mula.executorlinguagem.perl.ExecutorPerl;

public class ExecutorLinguagemFactory {

	private static ExecutorLinguagem executorLinguagemJava = new ExecutorJava();
	private static ExecutorLinguagem executorLinguagemNulo = new ExecutorNulo();
	private static ExecutorLinguagem executorLinguagemPerl = new ExecutorPerl();
	
	public static ExecutorLinguagem criaExecutor(String linguagem) {
		switch (linguagem.toUpperCase()) {
		case "JAVA":
			return executorLinguagemJava;
		case "PERL":
			return executorLinguagemPerl;
		default:
			return executorLinguagemNulo;
		}
	}

}
