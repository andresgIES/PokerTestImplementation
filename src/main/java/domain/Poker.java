package domain;

import exceptions.ExceptionValidationPoker;
import static constants.Constants.SIZE_HAND;

import java.util.List;

import enums.TipoMano;

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
	
	public Ganador validatePar(Mano manoJugador1, Mano manoJugador2) throws ExceptionValidationPoker{
		validateMano(manoJugador1);
		validateMano(manoJugador2);
		
		if(!manoJugador1.getTipoDeMano().equals(manoJugador2.getTipoDeMano())) {
			// TODO crear metodo para validar la jerarquia de los tipos de mano
		}

		final String valorParJugador1 = getValorCartaPar(manoJugador1.getCartas());
		final String valorParJugador2 = getValorCartaPar(manoJugador2.getCartas());
		
		if(valorParJugador1.isEmpty()) {
			return new Ganador(valorParJugador2, TipoMano.PAR);
		}
		if(valorParJugador2.isEmpty()) {
			return new Ganador(valorParJugador1, TipoMano.PAR);
		}
		if(valorParJugador1.equals(valorParJugador2)) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}

		return null;
	}
}
