package mula.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import mula.compilador.Mula;

public class MulaExecutor {

	private static final Mula mula = new Mula();
	
	public static void main(String[] args) {
		if(args.length > 0){
			String file = args[0];
			try {
				executar(file);
			} catch (Exception e) {
				System.err.println("Falha ao executar mula code."+ e.getMessage());
			}
		} else {
			System.err.println("Informe o nome do arquivo .mula a ser executado.");
		}
	}

	private static void executar(String file) throws IOException {
		String mulaCode = lerMulaCode(file);
		mula.execute(mulaCode);
	}

	private static String lerMulaCode(String file) throws IOException {
		StringBuilder mulaCode = new StringBuilder();
		Files.readAllLines(Paths.get(file)).forEach(mulaCode::append);
		return mulaCode.toString();
	}
	
}
