package mulla.compilador.somador;

public class SomadorNulo implements Somador<Object> {

	@Override
	public Object soma(Object a, Object b) {
		return 0;
	}

}
