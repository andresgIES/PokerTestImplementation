package domain;

import static constants.Constants.getIndexCardByValue;
import static constants.Constants.getValueCardByIndex;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.Palo;
import exceptions.ExceptionValidationPoker;

public final class PokerValidations {
	
	private static final int PAR = 2;
	private static final int TERNA = 3;
	private static final int INDICE_CARTA_10 = 8;
	
	private PokerValidations() {
		super();
	}	
	
	public static final int getValueHighCard(List<Carta> cards) {
		
		int maxValue = 0;

		for (Carta card : cards) {
			int valueIndexOfCard = getIndexCardByValue(card.getValor());
			if (valueIndexOfCard >= maxValue) {
				maxValue = valueIndexOfCard;
			}
		}
		return maxValue;
	}
	
	public static final String getValueCardPar(List<Carta> cards) {
		
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
	
	public static final Carta getCartaDoublePair(List<Carta> cards) {
		
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
	
	public static final int getValueTerna(List<Carta> cartas) {
		
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
	
	public static final int getCorrectSequence(List<Carta> hand) throws ExceptionValidationPoker {
		
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

	public static boolean allSameColor(List<Carta> cartas) {
		
		boolean allHaveSameColor = true;
		
		final Palo colorMazo = cartas.get(0).getPalo();
				
		for (Carta card : cartas) {
			if(!colorMazo.equals(card.getPalo())) {
				allHaveSameColor = false;
				break;
			}
		}
		return allHaveSameColor;
	}

}
