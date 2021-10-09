package constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Carta;
import enums.Palo;

public final class Constants {
	
	private Constants() {
		super();
	}
	
	public static final int SIZE_HAND = 5;
	
	public static final List<String> VALORES = Arrays.asList("2","3","4","5","6","7","8","9","10","J","K","Q","A");
	
	public static final Map<String, Carta> CARTAS = getCartas();
	
	private static Map<String, Carta> getCartas(){
				
		final Map<String, Carta> cartas = new HashMap<>();
				
		cartas.put("2C", new Carta(Palo.C,VALORES.get(0)));
		cartas.put("3C", new Carta(Palo.C,VALORES.get(1)));
		cartas.put("4C", new Carta(Palo.C,VALORES.get(2)));
		cartas.put("5C", new Carta(Palo.C,VALORES.get(3)));
		cartas.put("6C", new Carta(Palo.C,VALORES.get(4)));
		cartas.put("7C", new Carta(Palo.C,VALORES.get(5)));
		cartas.put("8C", new Carta(Palo.C,VALORES.get(6)));
		cartas.put("9C", new Carta(Palo.C,VALORES.get(7)));
		cartas.put("10C", new Carta(Palo.C,VALORES.get(8)));
		cartas.put("JC", new Carta(Palo.C,VALORES.get(9)));
		cartas.put("KC", new Carta(Palo.C,VALORES.get(10)));
		cartas.put("QC", new Carta(Palo.C,VALORES.get(11)));
		cartas.put("AC", new Carta(Palo.C,VALORES.get(12)));
		
		cartas.put("2D", new Carta(Palo.D,VALORES.get(0)));
		cartas.put("3D", new Carta(Palo.D,VALORES.get(1)));
		cartas.put("4D", new Carta(Palo.D,VALORES.get(2)));
		cartas.put("5D", new Carta(Palo.D,VALORES.get(3)));
		cartas.put("6D", new Carta(Palo.D,VALORES.get(4)));
		cartas.put("7D", new Carta(Palo.D,VALORES.get(5)));
		cartas.put("8D", new Carta(Palo.D,VALORES.get(6)));	
		cartas.put("9D", new Carta(Palo.D,VALORES.get(7)));
		cartas.put("10D", new Carta(Palo.D,VALORES.get(8)));
		cartas.put("JD", new Carta(Palo.D,VALORES.get(9)));
		cartas.put("KD", new Carta(Palo.D,VALORES.get(10)));
		cartas.put("QD", new Carta(Palo.D,VALORES.get(11)));
		cartas.put("AD", new Carta(Palo.D,VALORES.get(12)));
		
		cartas.put("2H", new Carta(Palo.H,VALORES.get(0)));
		cartas.put("3H", new Carta(Palo.H,VALORES.get(1)));
		cartas.put("4H", new Carta(Palo.H,VALORES.get(2)));
		cartas.put("5H", new Carta(Palo.H,VALORES.get(3)));
		cartas.put("6H", new Carta(Palo.H,VALORES.get(4)));
		cartas.put("7H", new Carta(Palo.H,VALORES.get(5)));
		cartas.put("8H", new Carta(Palo.H,VALORES.get(6)));
		cartas.put("9H", new Carta(Palo.H,VALORES.get(7)));
		cartas.put("10H", new Carta(Palo.H,VALORES.get(8)));
		cartas.put("JH", new Carta(Palo.H,VALORES.get(9)));
		cartas.put("KH", new Carta(Palo.H,VALORES.get(10)));
		cartas.put("QH", new Carta(Palo.H,VALORES.get(11)));
		cartas.put("AH", new Carta(Palo.H,VALORES.get(12)));
		
		cartas.put("2S", new Carta(Palo.S,VALORES.get(0)));
		cartas.put("3S", new Carta(Palo.S,VALORES.get(1)));
		cartas.put("4S", new Carta(Palo.S,VALORES.get(2)));
		cartas.put("5S", new Carta(Palo.S,VALORES.get(3)));
		cartas.put("6S", new Carta(Palo.S,VALORES.get(4)));
		cartas.put("7S", new Carta(Palo.S,VALORES.get(5)));
		cartas.put("8S", new Carta(Palo.S,VALORES.get(6)));
		cartas.put("9S", new Carta(Palo.S,VALORES.get(7)));
		cartas.put("10S", new Carta(Palo.S,VALORES.get(8)));
		cartas.put("JS", new Carta(Palo.S,VALORES.get(9)));
		cartas.put("KS", new Carta(Palo.S,VALORES.get(10)));
		cartas.put("QS", new Carta(Palo.S,VALORES.get(11)));
		cartas.put("AS", new Carta(Palo.S,VALORES.get(12)));

		return cartas;
	}
	
	public static String getValueCardByIndex(int index) {
		return VALORES.get(index);
	}
	
	public static Integer getIndexCardByValue(String valor) {
		return VALORES.indexOf(valor);
	}
	
}
