package mula.executorlinguagem.perl;

import java.io.IOException;

import mula.executorlinguagem.ExecutorLinguagem;

public class ExecutorPerl implements ExecutorLinguagem {

	private Object retorno = null;
	private final ExternalRunner runner = new ExternalRunner();
	
	@Override
	public void executa(String codigo) {
		try {
			retorno = runner.run("perl", codigo);
			if(retorno.toString().equals("VOID"))
				retorno = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean temRetorno() {
		return retorno != null;
	}

	@Override
	public Object getRetorno() {
		return retorno;
	}

}
