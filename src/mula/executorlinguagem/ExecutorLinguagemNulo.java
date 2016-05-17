package mula.executorlinguagem;

public class ExecutorLinguagemNulo implements ExecutorLinguagem {

	@Override
	public void executa(String valorDiretoOuValorDaVariavel) {
	}

	@Override
	public boolean temRetorno() {
		return false;
	}

	@Override
	public Object getRetorno() {
		return new Object();
	}

}
