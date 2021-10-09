package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.VALORES;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
	
	private static final int PAR = 2;
	private static final int TERNA = 3;
	private static final int INDICE_CARTA_10 = 8;

	
	private void validateHand(Mano mano) throws ExceptionValidationPoker {
		if(mano == null || mano.getCartas().isEmpty() || mano.getCartas().size() != SIZE_HAND || mano.getTipoDeMano() == null) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.HAND_INVALID);
		}
	}
			
	public Ganador getValidationByMayorHand(Mano handPlayer1, Mano handPlayer2) throws ExceptionValidationPoker {
		
		validateHand(handPlayer1);
		validateHand(handPlayer2);
		
		if(TipoMano.CARTA_ALTA.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateHighCard(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.PAR.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validatePar(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.DOBLE_PAR.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateDoblePar(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.TERNA.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateTerna(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.ESCALERA.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateStair(handPlayer1, handPlayer2);
		}
		
		return null;
	}

	public int validateMayorHand(Mano handPlayer1, Mano handPlayer2) {
		
		if(handPlayer1.getValorMano() == handPlayer2.getValorMano()) {
			return handPlayer1.getValorMano();
		}
		if(handPlayer1.getValorMano() > handPlayer2.getValorMano()) {
			return handPlayer1.getValorMano();
		}
		else {
			return handPlayer2.getValorMano();
		}
	}
	
	private String getValorCartaPar(List<Carta> cards) {
		
		String valueCardPar = "";
		
		for (Carta carta : cards) {
			
			final String valueCard = carta.getValor();
			int equalValorPar = 0;
			
			for (Carta card : cards) {
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
	
	private Ganador validatePar(Mano handPlayer1, Mano handPlayer2){
		
		final String valueParPlayer1 = getValorCartaPar(handPlayer1.getCartas());
		final String valueParPlayer2 = getValorCartaPar(handPlayer2.getCartas());
		
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
	
	private int getValueHighCard(List<Carta> cards) {
		
		int maxValue = 0;

		for (Carta card : cards) {
			int valueIndexOfCard = getIndexCardByValue(card.getValor());
			if (valueIndexOfCard >= maxValue) {
				maxValue = valueIndexOfCard;
			}
		}
		return maxValue;
	}
	
	
	private Ganador validateHighCard(Mano handPlayer1, Mano handPlayer2) {
		
		final int valueHighCardPlayer1 = getValueHighCard(handPlayer1.getCartas());
		final int valueHighCardPlayer2 = getValueHighCard(handPlayer2.getCartas());
		
		if(valueHighCardPlayer1 > valueHighCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueHighCardPlayer1), TipoMano.CARTA_ALTA);
		}
		if(valueHighCardPlayer2 > valueHighCardPlayer1) {
			return new Ganador(getValueCardByIndex(valueHighCardPlayer2), TipoMano.CARTA_ALTA);
		}
		if(valueHighCardPlayer2 == valueHighCardPlayer1) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}
		
		return null;
	}
	
	private Carta getCartaDoblePar(List<Carta> cards) {
		
		Carta cardDoublePar = null;
		
		for (Carta carta : cards) {
			
			final String valueCard = carta.getValor();
			int equalValorPar = 0;
			
			for (Carta card : cards) {
				if(card.getValor().equals(valueCard)) {
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
	
	private Ganador validateDoblePar(Mano handPlayer1, Mano handPlayer2) {
				
		final Carta firstParPlayer1 = getCartaDoblePar(handPlayer1.getCartas());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(handPlayer1.getCartas(), firstParPlayer1);
		final Carta secondParPlayer1 = getCartaDoblePar(cardsPlayer1Filtred);
		
		final Carta firstParPlayer2 = getCartaDoblePar(handPlayer2.getCartas());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(handPlayer2.getCartas(), firstParPlayer2);
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
	
	private int getValueTerna(List<Carta> cartas) {
		
		int valueCardTerna = 0;
		
		for (Carta carta : cartas) {
			
			final String valueCard = carta.getValor();
			int equalValorTerna = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valueCard)) {
					equalValorTerna++;
				}
			}
			
			if(equalValorTerna == TERNA) {
				valueCardTerna = getIndexCardByValue(valueCard);
				break;
			}
			
		}
				
		return valueCardTerna;

	}
	
	private Ganador validateTerna (Mano handPlayer1, Mano handPlaye1) {
		
		final int valueTernaCardPlayer1 = getValueTerna(handPlayer1.getCartas());
		final int valueTernaCardPlayer2 = getValueTerna(handPlaye1.getCartas());
		
		if (valueTernaCardPlayer1 > valueTernaCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueTernaCardPlayer1), TipoMano.TERNA);
		}
		if (valueTernaCardPlayer1 < valueTernaCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueTernaCardPlayer2), TipoMano.TERNA);
		}
		if(valueTernaCardPlayer1 == valueTernaCardPlayer2) {
			// TODO validacion desempate de la terna
		}
		
		return null;
	}
	
	
	private int getCorrectSequence(List<Carta> hand) throws ExceptionValidationPoker {
		
		int indexFirstCard = -1;
		
		Comparator<Carta> compareByValue = 
				(Carta c1, Carta c2) -> getIndexCardByValue(c1.getValor()).compareTo(getIndexCardByValue(c2.getValor()));
		Collections.sort(hand, compareByValue);
				
		int firstPosition = getIndexCardByValue(hand.get(0).getValor());
		
		if (firstPosition >= INDICE_CARTA_10 ) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.INVALID_STAIR);
		}
		
		if (getValueCardByIndex(firstPosition + 1).equals(hand.get(1).getValor()) && 
			getValueCardByIndex(firstPosition + 2).equals(hand.get(2).getValor()) &&
			getValueCardByIndex(firstPosition + 3).equals(hand.get(3).getValor()) &&
			getValueCardByIndex(firstPosition + 4).equals(hand.get(4).getValor())) {
			indexFirstCard = firstPosition;
		}
				
		return indexFirstCard;
	}
	
	private Ganador validateStair(Mano handPlayer1, Mano handPlayer2) throws ExceptionValidationPoker {
		
		final int beginStairPlayer1 = getCorrectSequence(handPlayer1.getCartas());
		final int beginStairPlayer2 = getCorrectSequence(handPlayer2.getCartas());
		
		if(beginStairPlayer1 > beginStairPlayer2) {
			return new Ganador(getValueCardByIndex(beginStairPlayer1), TipoMano.ESCALERA);
		}
		if(beginStairPlayer1 < beginStairPlayer2) {
			return new Ganador(getValueCardByIndex(beginStairPlayer2), TipoMano.ESCALERA);
		}
		if(beginStairPlayer1 == beginStairPlayer2) {
			// TODO implementar desempate escalera
		}
		
		return null;
	}
	




	private String getValueCardByIndex(int index) {
		return VALORES.get(index);
	}
	
	private Integer getIndexCardByValue(String valor) {
		return VALORES.indexOf(valor);
	}


		
}
