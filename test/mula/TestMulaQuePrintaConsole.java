package mula;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import mula.compilador.Mula;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMulaQuePrintaConsole {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private Mula mula;
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		mula = new Mula();
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}

	@Test
	public void testOut() {
		// @formatter:off
		mula.execute(
				"a = 1+3;" + 
				"out a * 2;"
			);
		//@formatter:on
		assertEquals("8\n", outContent.toString());
	}

	@Test
	public void testApenasPrintPerl() {
		mula.execute("run 'perl', <% " + "print \"Hello World\";" + "%>;");
		assertEquals("Hello World", outContent.toString());
	}

}
