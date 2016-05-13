package mulla.compilador.somador;

public interface Somador<E> {

	public static final Somador<Object> NULO = new SomadorNulo();

	public E soma(Object a, Object b);
	
}
