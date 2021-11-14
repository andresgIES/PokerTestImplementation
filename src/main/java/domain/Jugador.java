package domain;

import java.util.List;

public class Jugador {

	private String nombre;

	private Mano mano;

	public Jugador() {
		super();
	}

	public Jugador(String nombre, Mano mano) {
		super();
		this.nombre = nombre;
		this.mano = mano;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Mano getMano() {
		return mano;
	}

	public void setMano(Mano mano) {
		this.mano = mano;
	}

	public List<Carta> getCartasPlayer() {
		return this.getMano().getCartas();
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", mano=" + mano + "]";
	}

}
