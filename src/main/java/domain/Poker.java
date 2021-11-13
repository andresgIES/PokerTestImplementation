package domain;

import static constants.Constants.INDEX_NOT_FOUND;
import static constants.Constants.SIZE_HAND;
import static constants.Constants.getIndexCardByValue;
import static constants.Constants.getValueCardByIndex;
import static constants.Constants.isNotIndexNotFound;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import enums.Palo;
import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
	
	private static final int PAR = 2;
	private static final int TERNA = 3;
	private static final int POKER = 4;
	private static final int INDICE_CARTA_10 = 8;
	
	public Ganador getValidationByMayorHand(Mano handPlayer1, Mano handPlayer2) throws ExceptionValidationPoker {
				
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
		
		if(TipoMano.COLOR.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateColor(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.FULL_HOUSE.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateFullHouse(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.POKER.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validatePoker(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.ESCALERA_COLOR.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateStairColor(handPlayer1, handPlayer2);
		}
		
		if(TipoMano.ESCALERA_REAL.getValor() == validateMayorHand(handPlayer1, handPlayer2)) {
			return validateRealStair(handPlayer1, handPlayer2);
		}
		
		return null;
	}

	private int validateMayorHand(Mano handPlayer1, Mano handPlayer2) throws ExceptionValidationPoker {
		
		handPlayer1 = getTypeOfHand(handPlayer1);
		handPlayer2 = getTypeOfHand(handPlayer2);
		
		validateHand(handPlayer1);
		validateHand(handPlayer2);
		
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
	
	private void validateHand(Mano mano) throws ExceptionValidationPoker {
		if(mano == null || mano.getCartas().isEmpty() || mano.getCartas().size() != SIZE_HAND || mano.getTipoDeMano() == null) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.HAND_INVALID);
		}
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
	
	private Ganador validatePar(Mano handPlayer1, Mano handPlayer2){
		
		final int valueParPlayer1 = getValueCardPar(handPlayer1.getCartas());
		final int valueParPlayer2 = getValueCardPar(handPlayer2.getCartas());
				
		if((valueParPlayer1 > valueParPlayer2) && isNotIndexNotFound(valueParPlayer1)) {
			return new Ganador(getValueCardByIndex(valueParPlayer1), TipoMano.PAR);
		}
		if((valueParPlayer1 < valueParPlayer2) && isNotIndexNotFound(valueParPlayer2)) {
			return new Ganador(getValueCardByIndex(valueParPlayer2), TipoMano.PAR);
		}
		if(valueParPlayer1 == valueParPlayer2) {
			return validateHighCard(handPlayer1, handPlayer2);
		}

		return null;
	}
	
	private List<Carta> getNewListWithOutSpecificCard(final List<Carta> playerCards, final int cardToRemove) {
		if(!isNotIndexNotFound(cardToRemove)) {
			return playerCards;
		}
		else {
			return playerCards.stream().filter(carta -> 
			!carta.getValor().equals(getValueCardByIndex(cardToRemove)))
			.collect(Collectors.toList());
		}
	}
	
	private Ganador validateDoblePar(Mano handPlayer1, Mano handPlayer2) {
				
		final int firstParPlayer1 = getValueCardPar(handPlayer1.getCartas());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(handPlayer1.getCartas(), firstParPlayer1);
		final int secondParPlayer1 = getValueCardPar(cardsPlayer1Filtred);
		
		final int firstParPlayer2 = getValueCardPar(handPlayer2.getCartas());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(handPlayer2.getCartas(), firstParPlayer2);
		final int secondParPlayer2 = getValueCardPar(cardsPlayer2Filtred);
		
		final int bestDoblePairPlayer1 = (firstParPlayer1 > secondParPlayer1) ? firstParPlayer1 : secondParPlayer1;
		final int bestDoblePairPlayer2 = (firstParPlayer2 > secondParPlayer2) ? firstParPlayer2 : secondParPlayer2;
		
		if (bestDoblePairPlayer1 == bestDoblePairPlayer2 && validateBothIndex(bestDoblePairPlayer1, bestDoblePairPlayer2)){
			
			final int secondDoblePairPlayer1 = (firstParPlayer1 == bestDoblePairPlayer1) ? secondParPlayer1 : firstParPlayer1;
			final int secondDoblePairPlayer2 = (firstParPlayer2 == bestDoblePairPlayer2) ? secondParPlayer2 : firstParPlayer2;

			if ((secondDoblePairPlayer1 > secondDoblePairPlayer2) && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return getWinnerWith2Cards(firstParPlayer1, secondParPlayer1, TipoMano.DOBLE_PAR);
			}
			
			if ((secondDoblePairPlayer2 > secondDoblePairPlayer1) && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return getWinnerWith2Cards(firstParPlayer2, secondParPlayer2, TipoMano.DOBLE_PAR);
			}
			
			if (secondDoblePairPlayer1 == secondDoblePairPlayer2 && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				
			}
			
		}

		if ((bestDoblePairPlayer1 > bestDoblePairPlayer2) && validateBothIndex(firstParPlayer1, secondParPlayer1)) {
			return getWinnerWith2Cards(firstParPlayer1, secondParPlayer1, TipoMano.DOBLE_PAR);
		}
		
		if (bestDoblePairPlayer1 < bestDoblePairPlayer2 && validateBothIndex(firstParPlayer2, secondParPlayer2)) {
			return getWinnerWith2Cards(firstParPlayer2, secondParPlayer2, TipoMano.DOBLE_PAR);
		}
		
		return null;
	}
	
	private Ganador getWinnerWith2Cards(int cardIndex1, int intCardIndex2, TipoMano tipoDeMano) {
		final String winnerCards = getValueCardByIndex(cardIndex1).concat(", ").concat(getValueCardByIndex(intCardIndex2));
		return new Ganador(winnerCards, tipoDeMano);
	}
		
	private Ganador validateTerna (Mano handPlayer1, Mano handPlayer2) {
		
		final int valueTernaCardPlayer1 = getValueTerna(handPlayer1.getCartas());
		final int valueTernaCardPlayer2 = getValueTerna(handPlayer2.getCartas());
		
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
	
	private Ganador validateStair(Mano handPlayer1, Mano handPlayer2){
		
		final int beginStairPlayer1 = getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = getCorrectStair(handPlayer2.getCartas());
		
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
	
	private Ganador validateColor(Mano handPlayer1, Mano handPlayer2) {
		
		final boolean sameColorPlayer1 = allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = allSameColor(handPlayer2.getCartas());
				
		String colorGanador = "Gana el palo: ";
				
		if(sameColorPlayer1 && sameColorPlayer2) {
			// TODO implementar desempate de color
		}	

		if(sameColorPlayer1) {
			return new Ganador(colorGanador + (handPlayer1.getCartas().get(0).getPalo().getNombre()), TipoMano.COLOR);
		}
		if(sameColorPlayer2) {
			return new Ganador(colorGanador + (handPlayer2.getCartas().get(0).getPalo().getNombre()), TipoMano.COLOR);		}
	
		return null;
	}
	
	private Ganador validateFullHouse(Mano handPlayer1, Mano handPlayer2) {

		final int valueTernaPlayer1 = getValueTerna(handPlayer1.getCartas());
		final int valueTernaPlayer2 = getValueTerna(handPlayer2.getCartas());
		
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(handPlayer1.getCartas(), valueTernaPlayer1);
		final int parPlayer1 = getValueCardPar(cardsPlayer1Filtred);
		
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(handPlayer2.getCartas(), valueTernaPlayer2);
		final int parPlayer2 = getValueCardPar(cardsPlayer2Filtred);

		if(valueTernaPlayer1 > valueTernaPlayer2 && validateBothIndex(valueTernaPlayer1, parPlayer1)) {
			final String parAndTernaPlayer1 = getValueCardByIndex(valueTernaPlayer1).concat(", ").concat(getValueCardByIndex(parPlayer1));
			return new Ganador(parAndTernaPlayer1, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 < valueTernaPlayer2 && validateBothIndex(valueTernaPlayer2, parPlayer2)) {
			final String parAndTernaPlayer2 = getValueCardByIndex(valueTernaPlayer2).concat(", ").concat(getValueCardByIndex(parPlayer2));
			return new Ganador(parAndTernaPlayer2, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 == valueTernaPlayer2 && validateBothIndex(valueTernaPlayer1, valueTernaPlayer2)
				&& validateBothIndex(parPlayer1, parPlayer2)) {
			// TODO implementar desempate full house
		}

		return null;
	}
	
	private Ganador validatePoker(Mano handPlayer1, Mano handPlayer2) {
		
		final int valueCardPokerPlayer1 = getValuePoker(handPlayer1.getCartas());
		final int valueCardPokerPlayer2 = getValuePoker(handPlayer2.getCartas());
		
		if (valueCardPokerPlayer1 > valueCardPokerPlayer2) {
			return new Ganador(getValueCardByIndex(valueCardPokerPlayer1), TipoMano.POKER);
		}
		if (valueCardPokerPlayer1 < valueCardPokerPlayer2) {
			return new Ganador(getValueCardByIndex(valueCardPokerPlayer2), TipoMano.POKER);
		}
		if(valueCardPokerPlayer1 == valueCardPokerPlayer2) {
			// TODO validacion desempate del poker
		}
		
		return null;
	}
	
	private Ganador validateStairColor(Mano handPlayer1, Mano handPlayer2) {
		
		final boolean sameColorPlayer1 = allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = allSameColor(handPlayer2.getCartas());
		
		final int beginStairPlayer1 = getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = getCorrectStair(handPlayer2.getCartas());
		
		String colorGanador = "Gana la escalera de: ";
		
		if ((beginStairPlayer1 > beginStairPlayer2) && sameColorPlayer1) {
			return new Ganador(colorGanador + (handPlayer1.getCartas().get(0).getPalo().getNombre()), TipoMano.ESCALERA_COLOR);
		}
		if ((beginStairPlayer1 < beginStairPlayer2) && sameColorPlayer2) {
			return new Ganador(colorGanador + (handPlayer2.getCartas().get(0).getPalo().getNombre()), TipoMano.ESCALERA_COLOR);
		}
		if((beginStairPlayer1 == beginStairPlayer2) && (sameColorPlayer1 && sameColorPlayer2)) {
			// TODO validacion desempate de la escalera de color
		}
		
		return null;
	}
	
	private Ganador validateRealStair(Mano handPlayer1, Mano handPlayer2) {
		
		final boolean sameColorPlayer1 = allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = allSameColor(handPlayer2.getCartas());
		
		final int beginStairPlayer1 = getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = getCorrectStair(handPlayer2.getCartas());
		
		final boolean isFirstPositionTenPlayer1 = isFirstCardTen(beginStairPlayer1);
		final boolean isFirstPositionTenPlayer2 = isFirstCardTen(beginStairPlayer2);
		
		String colorGanador = "Gana la escalera real de: ";
		
		if((isFirstPositionTenPlayer1 && isFirstPositionTenPlayer2) && (sameColorPlayer1 && sameColorPlayer2)) {
			// TODO validacion desempate de la escalera de real
		}
		if (sameColorPlayer1 && isFirstPositionTenPlayer1) {
			return new Ganador(colorGanador + (handPlayer1.getCartas().get(0).getPalo().getNombre()), TipoMano.ESCALERA_REAL);
		}
		if (sameColorPlayer2 && isFirstPositionTenPlayer2) {
			return new Ganador(colorGanador + (handPlayer2.getCartas().get(0).getPalo().getNombre()), TipoMano.ESCALERA_REAL);
		}

		return null;
	}
	
	public Mano getTypeOfHand(Mano mano) {
		
		final boolean sameColor = allSameColor(mano.getCartas());
		final int beginStair = getCorrectStair(mano.getCartas());
		final boolean isFirstPositionTen = isFirstCardTen(beginStair);
		final int valueCardPoker= getValuePoker(mano.getCartas());

		if (sameColor && isFirstPositionTen) {
			mano.setTipoDeMano(TipoMano.ESCALERA_REAL);
			return mano;
		}
		
		if(sameColor && isNotIndexNotFound(beginStair)) {
			mano.setTipoDeMano(TipoMano.ESCALERA_COLOR);
			return mano;
		}
		
		if (isNotIndexNotFound(valueCardPoker)) {
			mano.setTipoDeMano(TipoMano.POKER);
			return mano;
		}
		
		final int valueTerna = getValueTerna(mano.getCartas());
		final List<Carta> cardsPlayerFiltred = getNewListWithOutSpecificCard(mano.getCartas(), valueTerna);
		final int parPlayer = getValueCardPar(cardsPlayerFiltred);

		if (validateBothIndex(valueTerna, parPlayer)) {
			mano.setTipoDeMano(TipoMano.FULL_HOUSE);
			return mano;
		}
		
		if (sameColor) {
			mano.setTipoDeMano(TipoMano.COLOR);
			return mano;
		}
		
		if(isNotIndexNotFound(beginStair)) {
			mano.setTipoDeMano(TipoMano.ESCALERA);
			return mano;
		}
		
		if(isNotIndexNotFound(valueTerna)) {
			mano.setTipoDeMano(TipoMano.TERNA);
			return mano;
		}
		
		final int firstPar = getValueCardPar(mano.getCartas());
		final List<Carta> cardsParFiltred = getNewListWithOutSpecificCard(mano.getCartas(), firstPar);
		final int secondPar = getValueCardPar(cardsParFiltred);
		
		if (validateBothIndex(firstPar, secondPar)) {
			mano.setTipoDeMano(TipoMano.DOBLE_PAR);
			return mano;
		}
		
		final int valuePar = getValueCardPar(mano.getCartas());

		if(isNotIndexNotFound(valuePar)) {
			mano.setTipoDeMano(TipoMano.PAR);
			return mano;
		}
		
		final int valueHighCard = getValueHighCard(mano.getCartas());

		if(isNotIndexNotFound(valueHighCard)) {
			mano.setTipoDeMano(TipoMano.CARTA_ALTA);
			return mano;
		}
		
		return mano;
	}
	

	private int getValueHighCard(final List<Carta> cards) {

		int maxValue = 0;

		for (Carta card : cards) {
			int valueIndexOfCard = getIndexCardByValue(card.getValor());
			if (valueIndexOfCard >= maxValue) {
				maxValue = valueIndexOfCard;
			}
		}
		return maxValue;
	}

	private int getValueCardPar(final List<Carta> cards) {
		return getValueEqualCard(cards, PAR);
	}

	private int getValueTerna(final List<Carta> cartas) {
		return getValueEqualCard(cartas, TERNA);
	}

	private int getCorrectStair(final List<Carta> hand){

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

	private boolean allSameColor(final List<Carta> cards) {

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
		
	private int getValuePoker(final List<Carta> cards){
		return getValueEqualCard(cards, POKER);
	}
	
	private static boolean isFirstCardTen(int valueFirstPosition){
		return INDICE_CARTA_10 == valueFirstPosition;
	}
		
	private boolean validateBothIndex(int indexOfPosition1, int indexOfPosition2){
		return INDEX_NOT_FOUND != indexOfPosition1 && INDEX_NOT_FOUND != indexOfPosition2;
	}

	private static int getValueEqualCard(final List<Carta> cards, int repetitions) {
		
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
