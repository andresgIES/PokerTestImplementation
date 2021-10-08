package enums;

public enum TipoMano {

	CARTA_ALTA(1), PAR(2), DOBLE_PAR(3), TERNA(4);

	private final int valor;

	private TipoMano(int valor) {
		this.valor = valor;
	}

	private TipoMano() {
		this.valor = 0;
	}

	public int getValor() {
		return valor;
	}

}
