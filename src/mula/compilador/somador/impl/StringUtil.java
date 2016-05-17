package mula.compilador.somador.impl;

public class StringUtil {

	public static String removeAspas(String a) {
		return a.replaceAll("'|\"", "");
	}

}
