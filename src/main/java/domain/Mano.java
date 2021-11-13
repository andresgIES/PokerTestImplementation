package domain;

import static constants.Constants.CARTAS;

import java.util.ArrayList;
import java.util.List;

import enums.TipoMano;

public class Mano {
		
	private List<Carta> cartas;
	
	private TipoMano tipoDeMano;
	
	public Mano() {
		super();
	}
	
	public Mano (String cartas) {
	    	
	    	List<Carta> listOfCartas = new ArrayList<>();
	    	
	        String data = cartas.trim();
	        String[] split = data.split(" ");
	        
	        for (String keyCard : split) {
				listOfCartas.add(CARTAS.get(keyCard));
			}
	           	
	    	this.cartas = listOfCartas;
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
	
	public int getValorMano() {
		return this.getTipoDeMano().getValor();
	}	
	
	public Carta getCartaByIndex(int indexCard) {
		return this.getCartas().get(indexCard);
	}

	@Override
	public String toString() {
		return "Mano [cartas=" + cartas + ", tipoDeMano=" + tipoDeMano + "]";
	}
	
}
