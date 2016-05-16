package mula;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import mulla.compilador.CompiladorTeste;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompiladorTestTestAcaoOut {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testOut(){
		//@formatter:on
		CompiladorTeste.test(
				"a = 1+3;"
				+"out a * 2;");
		//@formatter:off
		assertEquals("8", outContent.toString());
	}
	
}
