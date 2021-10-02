package domain;

import java.util.List;

public class Mano {
	
	private List<Carta> cartas;

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	@Override
	public String toString() {
		return "Baraja [cartas=" + cartas + "]";
	}
	
}
