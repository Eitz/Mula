package mula.compilador.somador.impl;

import mula.compilador.somador.Somador;

public class SomadorString implements Somador<String> {

	@Override
	public String soma(Object a, Object b) {
		a.toString().concat(b.toString());
		return StringUtil.removeAspas(a.toString());
	}

}
