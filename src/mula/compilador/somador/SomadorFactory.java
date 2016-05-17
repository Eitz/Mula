package mula.compilador.somador;

import mula.compilador.somador.impl.SomadorInteiros;
import mula.compilador.somador.impl.SomadorString;

public class SomadorFactory {

	private static final Somador<Integer> somadorInteiros = new SomadorInteiros();
	private static final Somador<String> somadorString = new SomadorString();

	public static Somador<?> criaSomador(Object a, Object b) {
		if (a instanceof Integer && b instanceof Integer) {
			return somadorInteiros;
		}
		return somadorString;
	}

}
