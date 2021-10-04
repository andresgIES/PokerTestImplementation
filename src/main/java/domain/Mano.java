package domain;

import java.util.List;

import enums.TipoMano;

public class Mano {
	
	private List<Carta> cartas;
	
	private TipoMano tipoDeMano;
	
	public Mano() {
		super();
	}

	public Mano(List<Carta> cartas, TipoMano tipoDeMano) {
		this.cartas = cartas;
		this.tipoDeMano = tipoDeMano;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public TipoMano getTipoDeMano() {
		return tipoDeMano;
	}

	public void setTipoDeMano(TipoMano tipoDeMano) {
		this.tipoDeMano = tipoDeMano;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	@Override
	public String toString() {
		return "Mano [cartas=" + cartas + ", tipoDeMano=" + tipoDeMano + "]";
	}
	
}
