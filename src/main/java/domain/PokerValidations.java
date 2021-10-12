package domain;

import static constants.Constants.INDEX_NOT_FOUND;
import static constants.Constants.getIndexCardByValue;
import static constants.Constants.getValueCardByIndex;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.Palo;

public final class PokerValidations {

	private static final int PAR = 2;
	private static final int TERNA = 3;
	private static final int POKER = 4;
	private static final int INDICE_CARTA_10 = 8;

	private PokerValidations() {
		super();
	}

	public static int getValueHighCard(List<Carta> cards) {

		int maxValue = 0;

		for (Carta card : cards) {
			int valueIndexOfCard = getIndexCardByValue(card.getValor());
			if (valueIndexOfCard >= maxValue) {
				maxValue = valueIndexOfCard;
			}
		}
		return maxValue;
	}

	public static int getValueCardPar(List<Carta> cards) {
		return getValueEqualCard(cards, PAR);
	}

	public static int getValueTerna(List<Carta> cartas) {
		return getValueEqualCard(cartas, TERNA);
	}

	public static int getCorrectStair(List<Carta> hand){

		int indexFirstCard = INDEX_NOT_FOUND;

		Comparator<Carta> compareByValue = (Carta c1, Carta c2) -> getIndexCardByValue(c1.getValor())
				.compareTo(getIndexCardByValue(c2.getValor()));
		Collections.sort(hand, compareByValue);

		int firstPosition = getIndexCardByValue(hand.get(0).getValor());

		if (getValueCardByIndex(firstPosition + 1).equals(hand.get(1).getValor())
				&& getValueCardByIndex(firstPosition + 2).equals(hand.get(2).getValor())
				&& getValueCardByIndex(firstPosition + 3).equals(hand.get(3).getValor())
				&& getValueCardByIndex(firstPosition + 4).equals(hand.get(4).getValor())) {
			indexFirstCard = firstPosition;
		}

		return indexFirstCard;
	}

	public static boolean allSameColor(List<Carta> cards) {

		boolean allHaveSameColor = true;

		final Palo colorMazo = cards.get(0).getPalo();

		for (Carta card : cards) {
			if (!colorMazo.equals(card.getPalo())) {
				allHaveSameColor = false;
				break;
			}
		}
		return allHaveSameColor;
	}
		
	public static int getValuePoker(List<Carta> cards){
		return getValueEqualCard(cards, POKER);
	}
	
	public static boolean isFirstCardTen(int valueFirstPosition){
		return INDICE_CARTA_10 == valueFirstPosition;
	}
		
	public static boolean validateBothIndex(int indexOfPosition1, int indexOfPosition2){
		return INDEX_NOT_FOUND != indexOfPosition1 && INDEX_NOT_FOUND != indexOfPosition2;
	}

	private static int getValueEqualCard(List<Carta> cards, int repetitions) {
		
		int indexOfCard = INDEX_NOT_FOUND;

		for (Carta carta : cards) {

			final String valueCard = carta.getValor();
			int contadorEqualCard = 0;

			for (Carta card : cards) {
				if (card.getValor().equals(valueCard)) {
					contadorEqualCard++;
				}
			}

			if (contadorEqualCard == repetitions) {
				indexOfCard = getIndexCardByValue(valueCard);
				break;
			}
		}
		return indexOfCard;
	}
		
}
