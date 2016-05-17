package mulla.executorlinguagem;

public class ExecutorLinguagemFactory {

	private static ExecutorLinguagem executorLinguagem;

	public static ExecutorLinguagem criaExecutor(String linguagem) {
		if (executorLinguagem == null)
			executorLinguagem = new ExecutorLinguagemNulo();
		return executorLinguagem;
	}

}
