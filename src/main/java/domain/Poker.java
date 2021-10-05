package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.VALORES;

import java.util.List;

import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
	
	private static final int PAR = 2;
	
	private void validateMano(Mano mano) throws ExceptionValidationPoker {
		if(mano == null || mano.getCartas().isEmpty() || mano.getCartas().size() != SIZE_HAND || mano.getTipoDeMano() == null) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.HAND_INVALID);
		}
	}
			
	public Ganador getValidationbyManoMayor(Mano manoJugador1, Mano manoJugador2) throws ExceptionValidationPoker {
		
		validateMano(manoJugador1);
		validateMano(manoJugador2);
		
		if(TipoMano.CARTA_ALTA.getValor() == validateManoMayor(manoJugador1, manoJugador2)) {
			return validateCartaAlta(manoJugador1, manoJugador2);
		}
		
		if(TipoMano.PAR.getValor() == validateManoMayor(manoJugador1, manoJugador2)) {
			return validatePar(manoJugador1, manoJugador2);
		}
		
		return null;
	}

	public int validateManoMayor (Mano manoJugador1, Mano manoJugador2) {
		
		if(manoJugador1.getValorMano() == manoJugador2.getValorMano()) {
			return manoJugador1.getValorMano();
		}
		if(manoJugador1.getValorMano() > manoJugador2.getValorMano()) {
			return manoJugador1.getValorMano();
		}
		else {
			return manoJugador2.getValorMano();
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
	
	private Ganador validatePar(Mano manoJugador1, Mano manoJugador2){
		
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
	
	private int getValorCartaAlta(List<Carta> cartas) {
		int valorMaximo = 0;

		for (Carta card : cartas) {
			int valorCartaInt = VALORES.indexOf(card.getValor());
			if (valorCartaInt >= valorMaximo) {
				valorMaximo = valorCartaInt;
			}

		}
		return valorMaximo;
	}
	
	
	private Ganador validateCartaAlta(Mano manoJugador1, Mano manoJugador2) {
		
		final int valorCartaAltaJugador1 = getValorCartaAlta(manoJugador1.getCartas());
		final int valorCartaAltaJugador2 = getValorCartaAlta(manoJugador2.getCartas());
		
		if(valorCartaAltaJugador1 > valorCartaAltaJugador2) {
			return new Ganador(getValorCartaByIndex(valorCartaAltaJugador1), TipoMano.CARTA_ALTA);
		}
		if(valorCartaAltaJugador2 > valorCartaAltaJugador1) {
			return new Ganador(getValorCartaByIndex(valorCartaAltaJugador2), TipoMano.CARTA_ALTA);
		}
		if(valorCartaAltaJugador2 == valorCartaAltaJugador1) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}
		
		return null;
	}
	
	private String getValorCartaByIndex(int index) {
		return VALORES.get(index);
	}


		
}
