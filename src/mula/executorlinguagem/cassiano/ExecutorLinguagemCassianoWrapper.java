package mula.executorlinguagem.cassiano;

import mula.executorlinguagem.ExecutorLinguagem;

public class ExecutorLinguagemCassianoWrapper implements ExecutorLinguagem {

	private final ExecutorCodigoJava executor = new ExecutorCodigoJava();
	
	@Override
	public void executa(String codigo) {
		executor.executar(codigo);
	}

	@Override
	public boolean temRetorno() {
		return executor.getResultado() != null;
	}

	@Override
	public Object getRetorno() {
		return executor.getResultado();
	}

}
