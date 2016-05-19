package mula.compilador.somador;

import mula.compilador.somador.impl.SomadorInteiros;
import mula.compilador.somador.impl.SomadorString;

public class SomadorFactory {

	private static final Somador<Integer> somadorInteiros = new SomadorInteiros();
	private static final Somador<String> somadorString = new SomadorString();

	public static Somador<?> criaSomador(Object a, Object b) {
		if (inteiros(a,b)) {
			return somadorInteiros;
		}
		return somadorString;
	}

	private static boolean inteiros(Object a, Object b) {
		try {
			Integer.parseInt(a.toString());
			Integer.parseInt(b.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
