package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.VALORES;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		
		if(TipoMano.DOBLE_PAR.getValor() == validateManoMayor(manoJugador1, manoJugador2)) {
			return validateDoblePar(manoJugador1, manoJugador2);
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
		
		String valueCardPar = "";
		
		for (Carta carta : cartas) {
			
			final String valueCard = carta.getValor();
			int equalValorPar = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valueCard)) {
					equalValorPar++;
				}
			}
			
			if(equalValorPar == PAR) {
				valueCardPar = valueCard;
			}
			
		}
		return valueCardPar;
	}
	
	private Ganador validatePar(Mano manoJugador1, Mano manoJugador2){
		
		final String valueParPlayer1 = getValorCartaPar(manoJugador1.getCartas());
		final String valueParPlayer2 = getValorCartaPar(manoJugador2.getCartas());
		
		if(valueParPlayer1.isEmpty()) {
			return new Ganador(valueParPlayer2, TipoMano.PAR);
		}
		if(valueParPlayer2.isEmpty()) {
			return new Ganador(valueParPlayer1, TipoMano.PAR);
		}
		if(valueParPlayer1.equals(valueParPlayer2)) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}

		return null;
	}
	
	private int getValueHighCard(List<Carta> cartas) {
		int maxValue = 0;

		for (Carta card : cartas) {
			int valorCartaInt = VALORES.indexOf(card.getValor());
			if (valorCartaInt >= maxValue) {
				maxValue = valorCartaInt;
			}
		}
		return maxValue;
	}
	
	
	private Ganador validateCartaAlta(Mano manoJugador1, Mano manoJugador2) {
		
		final int valueHighCardPlayer1 = getValueHighCard(manoJugador1.getCartas());
		final int balueHighCardPlayer2 = getValueHighCard(manoJugador2.getCartas());
		
		if(valueHighCardPlayer1 > balueHighCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueHighCardPlayer1), TipoMano.CARTA_ALTA);
		}
		if(balueHighCardPlayer2 > valueHighCardPlayer1) {
			return new Ganador(getValueCardByIndex(balueHighCardPlayer2), TipoMano.CARTA_ALTA);
		}
		if(balueHighCardPlayer2 == valueHighCardPlayer1) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}
		
		return null;
	}
	
	private Carta getCartaDoblePar(List<Carta> cartas) {
		
		Carta cardDoublePar = null;
		
		for (Carta carta : cartas) {
			
			final String valorCarta = carta.getValor();
			int equalValorPar = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valorCarta)) {
					equalValorPar++;
				}
			}
			
			if(equalValorPar == PAR) {
				cardDoublePar = carta;
				break;
			}
			
		}
		return cardDoublePar;
	}
	
	private List<Carta> getNewListWithOutSpecificCard(List<Carta> playerCards, final Carta cardToRemove) {
		if(Objects.nonNull(cardToRemove)) {
			playerCards = playerCards.stream().filter(carta -> 
			!carta.getValor().equals(cardToRemove.getValor()))
			.collect(Collectors.toList());
		}
		return playerCards;
	}
	
	private Ganador validateDoblePar(Mano manoJugador1, Mano manoJugador2) {
				
		final Carta firstParPlayer1 = getCartaDoblePar(manoJugador1.getCartas());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(manoJugador1.getCartas(), firstParPlayer1);
		final Carta secondParPlayer1 = getCartaDoblePar(cardsPlayer1Filtred);
		
		final Carta firstParPlayer2 = getCartaDoblePar(manoJugador2.getCartas());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(manoJugador2.getCartas(), firstParPlayer2);
		final Carta segundoParJugador2 = getCartaDoblePar(cardsPlayer2Filtred);
		
		final boolean noNullParsPlayer1 = Objects.nonNull(firstParPlayer1) && Objects.nonNull(secondParPlayer1);
		final boolean noNullParsPlayer2 = Objects.nonNull(firstParPlayer2) && Objects.nonNull(segundoParJugador2);

		if (noNullParsPlayer1 && !noNullParsPlayer2) {
			final String winnerCards = firstParPlayer1.getValor().concat(", ").concat(secondParPlayer1.getValor());
			return new Ganador(winnerCards, TipoMano.DOBLE_PAR);
		}
		
		if (!noNullParsPlayer1 && noNullParsPlayer2) {
			final String winnerCards = firstParPlayer2.getValor().concat(", ").concat(segundoParJugador2.getValor());
			return new Ganador(winnerCards, TipoMano.DOBLE_PAR);
		}
		
		if (noNullParsPlayer1 && noNullParsPlayer2){
			// TODO implementar empate de doble par
		}
				
		return new Ganador();
	}
	
	private String getValueCardByIndex(int index) {
		return VALORES.get(index);
	}


		
}
