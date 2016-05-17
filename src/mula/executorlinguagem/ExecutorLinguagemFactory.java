package mula.executorlinguagem;

import mula.executorlinguagem.cassiano.ExecutorLinguagemCassianoWrapper;

public class ExecutorLinguagemFactory {

	private static ExecutorLinguagem executorLinguagemJava = new ExecutorLinguagemCassianoWrapper();
	private static ExecutorLinguagem executorLinguagemNulo = new ExecutorLinguagemNulo();
	
	public static ExecutorLinguagem criaExecutor(String linguagem) {
		switch (linguagem.toUpperCase()) {
		case "JAVA":
			return executorLinguagemJava;
		default:
			return executorLinguagemNulo;
		}
	}

}
