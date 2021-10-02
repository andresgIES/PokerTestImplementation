package domain;

import exceptions.ExceptionValidationPoker;
import static constants.Constants.SIZE_HAND;

import java.util.List;

public class Poker {
	
	private static final int PAR = 2;
	
	private void validateMano(Mano mano) throws ExceptionValidationPoker {
		if(mano == null || mano.getCartas().isEmpty() || mano.getCartas().size() != SIZE_HAND) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.HAND_INVALID);
		}
	}
	
	private String getValorCartaPar(List<Carta> cartas) {
		
		String valorCartaMano = "";
		
		for (Carta carta : cartas) {
			
			final String valorCarta = carta.getValor();
			int valoresIguales = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valorCarta)) {
					valoresIguales++;
				}
			}
			
			if(valoresIguales == PAR) {
				valorCartaMano = valorCarta;
			}
		
		}
		return valorCartaMano;
	}
	
	public Mano validatePar(Mano manoJugador1, Mano manoJugador2) throws ExceptionValidationPoker{
		validateMano(manoJugador1);
		validateMano(manoJugador2);

		final String valorParJugador1 = getValorCartaPar(manoJugador1.getCartas());
		final String valorParJugador2 = getValorCartaPar(manoJugador2.getCartas());
		
		System.out.println("valorParJugador1: " + valorParJugador1);
		System.out.println("valorParJugador2: " + valorParJugador2);

		if(valorParJugador1.isEmpty()) {
			return manoJugador2;
		}
		if(valorParJugador2.isEmpty()) {
			return manoJugador1;
		}
		if(valorParJugador1.equals(valorParJugador2)) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}

		return null;
	}
}
