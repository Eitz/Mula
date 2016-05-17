package mula.compilador;

public class Variavel {
	private Tipo tipo;
	private String nome;
	private Object valor;

	public Variavel(Tipo tipo, String nome, Object valor) {
		super();
		this.tipo = tipo;
		this.nome = nome;
		this.valor = valor;
	}

	public Variavel(String nome) {
		this.nome = nome;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "[" + nome + " = " + valor + "]";
	}

}
