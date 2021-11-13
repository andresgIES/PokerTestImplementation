package domain;

public class Ganador {
	
	private String nombreJugador;

	private Mano mano;
	
	private String cartaGanadora;
		
	public Ganador() {
		super();
	}
	
	public Ganador(String cartaGanadora, Mano mano, String nombreJugador) {
		super();
		this.nombreJugador = nombreJugador;
		this.mano = mano;
		this.cartaGanadora = cartaGanadora;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public Mano getMano() {
		return mano;
	}

	public void setMano(Mano mano) {
		this.mano = mano;
	}

	public String getCartaGanadora() {
		return cartaGanadora;
	}

	public void setCartaGanadora(String cartaGanadora) {
		this.cartaGanadora = cartaGanadora;
	}

	public String getMessageWinner() {
		return "El ganador es el jugador: " + this.nombreJugador + " Con la mano de tipo: " + this.getMano().getTipoDeMano();
	}
	
	public static Ganador getWinnerWith2Cards(String card1, String card2, Mano mano, String jugador) {
		final String winnerCards = card1.concat(", ").concat(card2);
		return new Ganador(winnerCards, mano, jugador);
	}

	@Override
	public String toString() {
		return "Ganador [nombreJugador=" + nombreJugador + ", mano=" + mano + ", cartaGanadora=" + cartaGanadora + "]";
	}

	
}
