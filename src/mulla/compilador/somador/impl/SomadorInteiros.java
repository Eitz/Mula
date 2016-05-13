package mulla.compilador.somador.impl;

import mulla.compilador.somador.Somador;

public class SomadorInteiros implements Somador<Integer> {

	@Override
	public Integer soma(Object a, Object b) {
		int intA = Integer.parseInt(a.toString());
		int intB = Integer.parseInt(b.toString());
		return intA + intB;
	}

}
