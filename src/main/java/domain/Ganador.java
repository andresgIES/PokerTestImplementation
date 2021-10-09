package domain;

import enums.TipoMano;

public class Ganador {
	
	private String cartaGanadora;
	
	private TipoMano tipoManoGanadora;
	
	public Ganador() {
		super();
	}
	
	public Ganador(String cartaGanadora, TipoMano tipoDeMano) {
		super();
		this.cartaGanadora = cartaGanadora;
		this.tipoManoGanadora = tipoDeMano;
	}

	public String getCartaGanadora() {
		return cartaGanadora;
	}

	public void setCartaGanadora(String cartaGanadora) {
		this.cartaGanadora = cartaGanadora;
	}

	public TipoMano getTipoDeMano() {
		return tipoManoGanadora;
	}

	public void setTipoDeMano(TipoMano tipoManoGanadora) {
		this.tipoManoGanadora = tipoManoGanadora;
	}

	@Override
	public String toString() {
		return "Ganador [cartaGanadora=" + cartaGanadora + ", tipoManoGanadora=" + tipoManoGanadora + "]";
	}
	
}
