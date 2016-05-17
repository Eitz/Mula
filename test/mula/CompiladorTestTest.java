package mula;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import mula.compilador.CompiladorTeste;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompiladorTestTest {

	private static final String TEXTO_ARQUIVO_TESTE = "A variável deve tem texto deste arquivo.";
	private static final String VAR_TXT = "var.txt";
	private static final String TEXTO_ESCRITO_TXT = "textoEscrito.txt";
	private static final String ARQUIVO_TESTE = "teste.txt";
	private static final String ARQUIVO_TESTE_BLA_BLA = "textoBlaBla.txt";

	@Before
	public void setup() throws Exception {
		Files.write(Paths.get(VAR_TXT), Arrays.asList("37"));
		Files.write(Paths.get(ARQUIVO_TESTE),
				Arrays.asList("A variável deve tem texto deste arquivo."));
	}

	@After
	public void limpa() {
		new File(ARQUIVO_TESTE).delete();
		new File(TEXTO_ESCRITO_TXT).delete();
		new File(VAR_TXT).delete();
		new File(ARQUIVO_TESTE_BLA_BLA).delete();
	}

	@Test
	public void test() {
		//@formatter:off
		CompiladorTeste.test(
				"a = 2 * (3 + 5);" + 
				"b = 14 * (10 / 5);"
			);
		//@formatter:on
		assertValoresSomaCorretos();
	}

	private void assertValoresSomaCorretos() {
		assertEquals(28, valorDe("b"));
		assertEquals(16, valorDe("a"));
	}

	@Test
	public void test_2() {
		//@formatter:off
		CompiladorTeste.test(
				"a = 2;"
			  + "a = a * 3;");
		//@formatter:on
		assertEquals(6, valorDe("a"));
	}

	@Test
	public void testLeituraArquivo() {
		CompiladorTeste.test("a <- 'teste.txt';");
		assertEquals(TEXTO_ARQUIVO_TESTE, valorDe("a"));
	}

	@Test
	public void testLerArquivoJava8() throws Exception {
		Files.write(Paths.get(ARQUIVO_TESTE),
				Arrays.asList(TEXTO_ARQUIVO_TESTE));
		File file = new File(ARQUIVO_TESTE);
		assertTrue(file.exists());
		String string = leTexto(file);
		assertEquals(TEXTO_ARQUIVO_TESTE, string);
	}

	private String leTexto(File file) {
		StringBuilder texto = new StringBuilder();
		try {
			Files.readAllLines(file.toPath()).forEach(texto::append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto.toString();
	}

	@Test
	public void testEscritaArquivo() {
		CompiladorTeste.test("'string do texto' -> '" + TEXTO_ESCRITO_TXT
				+ "';");
		assertEquals("string do texto", leTexto(new File(TEXTO_ESCRITO_TXT)));
	}

	@Test
	public void testEscritaArquivoVariavel() {
		CompiladorTeste.test(
// @formatter:off
			"texto = 'bla bla';" + 
			"texto -> 'textoBlaBla.txt';");
// @formatter:on
		assertEquals("bla bla", leTexto(new File("textoBlaBla.txt")));
	}

	@Test
	public void executaJava() {
		CompiladorTeste.test(
// @formatter:off
			"var <- 'var.txt';"+ // var.txt = 37
			"var2 = 4; "+
			"c = 50; " +
			"c = run 'java', <%" +
			"	System.out.println( %=c ); " +
			"	int soma = %=var + %=var2; " +
			"	return soma; " +
			" %>;"
			);
// @formatter:on
		assertEquals(41, valorDe("c"));
	}

	@Test
	public void executaJavaMostraNome() {
		CompiladorTeste.test(
// @formatter:off
			"nomeCassiano = 'Cassiano';" +
			"run 'java', <%"
			+ " System.out.println(\"%=nomeCassiano\");" +
			" %>;"
			);
// @formatter:on
	}
	

	private Object valorDe(String key) {
		return CompiladorTeste.variaveis.get(key).getValor();
	}

}
