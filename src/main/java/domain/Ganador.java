package domain;

import enums.TipoMano;

public class Ganador {

	private Jugador jugador;

	private String cartaGanadora;

	private TipoMano manoGanadora;

	public Ganador() {
		super();
	}

	public Ganador(String cartaGanadora, Jugador jugador, TipoMano manoGanadora) {
		super();
		this.jugador = jugador;
		this.cartaGanadora = cartaGanadora;
		this.manoGanadora = manoGanadora;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public String getCartaGanadora() {
		return cartaGanadora;
	}

	public void setCartaGanadora(String cartaGanadora) {
		this.cartaGanadora = cartaGanadora;
	}

	public TipoMano getManoGanadora() {
		return manoGanadora;
	}

	public void setManoGanadora(TipoMano manoGanadora) {
		this.manoGanadora = manoGanadora;
	}

	public String getMessageWinner() {
		return "El ganador es el jugador: " + this.jugador.getNombre() + " Con la mano de tipo: " + this.getManoGanadora();
	}

	public static Ganador getWinnerWith2Cards(String card1, String card2, Jugador jugador, TipoMano manoGanadora) {
		final String winnerCards = card1.concat(", ").concat(card2);
		return new Ganador(winnerCards, jugador, 	manoGanadora);
	}

	@Override
	public String toString() {
		return "Ganador [jugador=" + jugador + ", cartaGanadora=" + cartaGanadora + ", manoGanadora=" + manoGanadora + "]";
	}
	
}
