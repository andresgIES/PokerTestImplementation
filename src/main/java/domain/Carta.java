package domain;

import enums.Palo;

public class Carta {
	
	private Palo palo;
	
	private String valor;
	
	public Carta() {
		super();
	}

	public Carta(Palo palo, String valor) {
		this.palo = palo;
		this.valor = valor;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Carta [palo=" + palo + ", valor=" + valor + "]";
	}
	
}
