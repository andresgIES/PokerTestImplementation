package domain;

import enums.TipoMano;

public class Ganador {
	
	private String cartaGanadora;
	
	private TipoMano tipoDeMano;
	
	public Ganador() {
		super();
	}
	
	public Ganador(String cartaGanadora, TipoMano tipoDeMano) {
		super();
		this.cartaGanadora = cartaGanadora;
		this.tipoDeMano = tipoDeMano;
	}

	public String getCartaGanadora() {
		return cartaGanadora;
	}

	public void setCartaGanadora(String cartaGanadora) {
		this.cartaGanadora = cartaGanadora;
	}

	public TipoMano getTipoDeMano() {
		return tipoDeMano;
	}

	public void setTipoDeMano(TipoMano tipoDeMano) {
		this.tipoDeMano = tipoDeMano;
	}
	
	

}
