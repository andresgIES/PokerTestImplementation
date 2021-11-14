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
		
	public Ganador getValidationByBestHand(Jugador player1, Jugador player2) throws ExceptionValidationPoker {
						
		if(TipoMano.CARTA_ALTA.getValor() == validateBestHand(player1, player2)) {
			return validateHighCard(player1, player2);
		}
		
		if(TipoMano.PAR.getValor() == validateBestHand(player1, player2)) {
			return validatePar(player1, player2);
		}
		
		if(TipoMano.DOBLE_PAR.getValor() == validateBestHand(player1, player2)) {
			return validateDoblePar(player1, player2);
		}
		
		if(TipoMano.TERNA.getValor() == validateBestHand(player1, player2)) {
			return validateTerna(player1, player2);
		}
		
		if(TipoMano.ESCALERA.getValor() == validateBestHand(player1, player2)) {
			return validateStair(player1, player2);
		}
		
		if(TipoMano.COLOR.getValor() == validateBestHand(player1, player2)) {
			return validateColor(player1, player2);
		}
		
		if(TipoMano.FULL_HOUSE.getValor() == validateBestHand(player1, player2)) {
			return validateFullHouse(player1, player2);
		}
		
		if(TipoMano.POKER.getValor() == validateBestHand(player1, player2)) {
			return validatePoker(player1, player2);
		}
		
		if(TipoMano.ESCALERA_COLOR.getValor() == validateBestHand(player1, player2)) {
			return validateStairColor(player1, player2);
		}
		
		if(TipoMano.ESCALERA_REAL.getValor() == validateBestHand(player1, player2)) {
			return validateRealStair(player1, player2);
		}
		
		return null;
	}

	private int validateBestHand(Jugador player1, Jugador player2) throws ExceptionValidationPoker {
		
		final Mano playerHand1 = getTypeOfHand(player1.getMano());
		final Mano playerHand2 = getTypeOfHand(player2.getMano());
		
		validateHand(playerHand1);
		validateHand(playerHand2);
		
		if(playerHand1.getValorMano() == playerHand2.getValorMano()) {
			return playerHand1.getValorMano();
		}
		if(playerHand1.getValorMano() > playerHand2.getValorMano()) {
			return playerHand1.getValorMano();
		}
		else {
			return playerHand2.getValorMano();
		}
	}
	
	private void validateHand(Mano mano) throws ExceptionValidationPoker {
		if(mano == null || mano.getCartas().isEmpty() || mano.getCartas().size() != SIZE_HAND || mano.getTipoDeMano() == null) {
			throw new ExceptionValidationPoker(ExceptionValidationPoker.HAND_INVALID);
		}
	}
	
	private Ganador validateHighCard(Jugador player1, Jugador player2) {
		
		final int valueHighCardPlayer1 = getValueHighCard(player1.getCartasPlayer());
		final int valueHighCardPlayer2 = getValueHighCard(player2.getCartasPlayer());
		
		if(valueHighCardPlayer1 > valueHighCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueHighCardPlayer1), player1, TipoMano.CARTA_ALTA);
		}
		if(valueHighCardPlayer2 > valueHighCardPlayer1) {
			return new Ganador(getValueCardByIndex(valueHighCardPlayer2), player2, TipoMano.CARTA_ALTA);
		}
		if(valueHighCardPlayer2 == valueHighCardPlayer1) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}
		
		return null;
	}
	
	private Ganador validatePar(Jugador player1, Jugador player2){
		
		final int valueParPlayer1 = getValueCardPar(player1.getCartasPlayer());
		final int valueParPlayer2 = getValueCardPar(player2.getCartasPlayer());
				
		if((valueParPlayer1 > valueParPlayer2) && isNotIndexNotFound(valueParPlayer1)) {
			return new Ganador(getValueCardByIndex(valueParPlayer1), player1, TipoMano.PAR);
		}
		if((valueParPlayer1 < valueParPlayer2) && isNotIndexNotFound(valueParPlayer2)) {
			return new Ganador(getValueCardByIndex(valueParPlayer2), player2, TipoMano.PAR);
		}
		if(valueParPlayer1 == valueParPlayer2) {
			return validateHighCard(player1, player2);
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
	
	private Ganador validateDoblePar(Jugador player1, Jugador player2) {
				
		final int firstParPlayer1 = getValueCardPar(player1.getCartasPlayer());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(player1.getCartasPlayer(), firstParPlayer1);
		final int secondParPlayer1 = getValueCardPar(cardsPlayer1Filtred);
		
		final int firstParPlayer2 = getValueCardPar(player2.getCartasPlayer());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(player2.getCartasPlayer(), firstParPlayer2);
		final int secondParPlayer2 = getValueCardPar(cardsPlayer2Filtred);
		
		final int bestDoblePairPlayer1 = (firstParPlayer1 > secondParPlayer1) ? firstParPlayer1 : secondParPlayer1;
		final int bestDoblePairPlayer2 = (firstParPlayer2 > secondParPlayer2) ? firstParPlayer2 : secondParPlayer2;
		
		if (bestDoblePairPlayer1 == bestDoblePairPlayer2 && validateBothIndex(bestDoblePairPlayer1, bestDoblePairPlayer2)){
			
			final int secondDoblePairPlayer1 = (firstParPlayer1 == bestDoblePairPlayer1) ? secondParPlayer1 : firstParPlayer1;
			final int secondDoblePairPlayer2 = (firstParPlayer2 == bestDoblePairPlayer2) ? secondParPlayer2 : firstParPlayer2;

			if ((secondDoblePairPlayer1 > secondDoblePairPlayer2) && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return Ganador.getWinnerWith2Cards(
						getValueCardByIndex(firstParPlayer1), getValueCardByIndex(secondParPlayer1), player1, TipoMano.DOBLE_PAR);
			}
			
			if ((secondDoblePairPlayer2 > secondDoblePairPlayer1) && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return Ganador.getWinnerWith2Cards(
						getValueCardByIndex(firstParPlayer2), getValueCardByIndex(secondParPlayer2), player2, TipoMano.DOBLE_PAR);
			}
			
			if (secondDoblePairPlayer1 == secondDoblePairPlayer2 && validateBothIndex(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				// TODO desempate de doble par
			}
			
		}

		if ((bestDoblePairPlayer1 > bestDoblePairPlayer2) && validateBothIndex(firstParPlayer1, secondParPlayer1)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(firstParPlayer1),getValueCardByIndex(secondParPlayer1), player1, TipoMano.DOBLE_PAR);
		}
		
		if (bestDoblePairPlayer1 < bestDoblePairPlayer2 && validateBothIndex(firstParPlayer2, secondParPlayer2)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(firstParPlayer2), getValueCardByIndex(secondParPlayer2), player2, TipoMano.DOBLE_PAR);
		}
		
		return null;
	}
	
		
	private Ganador validateTerna (Jugador player1, Jugador player2) {
		
		final int valueTernaCardPlayer1 = getValueTerna(player1.getCartasPlayer());
		final int valueTernaCardPlayer2 = getValueTerna(player2.getCartasPlayer());
		
		if (valueTernaCardPlayer1 > valueTernaCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueTernaCardPlayer1), player1, TipoMano.TERNA);
		}
		if (valueTernaCardPlayer1 < valueTernaCardPlayer2) {
			return new Ganador(getValueCardByIndex(valueTernaCardPlayer2), player2, TipoMano.TERNA);
		}
		if(valueTernaCardPlayer1 == valueTernaCardPlayer2) {
			// TODO validacion desempate de la terna
		}
		
		return null;
	}
	
	private Ganador validateStair(Jugador player1, Jugador player2){
		
		final int beginStairPlayer1 = getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = getCorrectStair(player2.getCartasPlayer());
		
		if(beginStairPlayer1 > beginStairPlayer2) {
			return new Ganador(getValueCardByIndex(beginStairPlayer1), player1, TipoMano.ESCALERA);
		}
		if(beginStairPlayer1 < beginStairPlayer2) {
			return new Ganador(getValueCardByIndex(beginStairPlayer2), player2, TipoMano.ESCALERA);
		}
		if(beginStairPlayer1 == beginStairPlayer2) {
			// TODO implementar desempate escalera
		}
		
		return null;	
	}
	
	private Ganador validateColor(Jugador player1, Jugador player2) {
		
		final boolean sameColorPlayer1 = allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = allSameColor(player2.getCartasPlayer());
								
		if(sameColorPlayer1 && sameColorPlayer2) {
			// TODO implementar desempate de color
		}	

		if(sameColorPlayer1) {
			return new Ganador(Carta.getNamePalo(player1), player1, TipoMano.COLOR);
		}
		if(sameColorPlayer2) {
			return new Ganador(Carta.getNamePalo(player1), player2, TipoMano.COLOR);		
		}
	
		return null;
	}
	
	private Ganador validateFullHouse(Jugador player1, Jugador player2) {

		final int valueTernaPlayer1 = getValueTerna(player1.getCartasPlayer());
		final int valueTernaPlayer2 = getValueTerna(player2.getCartasPlayer());
		
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(player1.getCartasPlayer(), valueTernaPlayer1);
		final int parPlayer1 = getValueCardPar(cardsPlayer1Filtred);
		
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(player2.getCartasPlayer(), valueTernaPlayer2);
		final int parPlayer2 = getValueCardPar(cardsPlayer2Filtred);

		if(valueTernaPlayer1 > valueTernaPlayer2 && validateBothIndex(valueTernaPlayer1, parPlayer1)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(valueTernaPlayer1), getValueCardByIndex(parPlayer1), player1, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 < valueTernaPlayer2 && validateBothIndex(valueTernaPlayer2, parPlayer2)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(valueTernaPlayer2), getValueCardByIndex(parPlayer2), player2, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 == valueTernaPlayer2 && validateBothIndex(valueTernaPlayer1, valueTernaPlayer2)
				&& validateBothIndex(parPlayer1, parPlayer2)) {
			// TODO implementar desempate full house
		}

		return null;
	}
	
	private Ganador validatePoker(Jugador player1, Jugador player2) {
		
		final int valueCardPokerPlayer1 = getValuePoker(player1.getCartasPlayer());
		final int valueCardPokerPlayer2 = getValuePoker(player2.getCartasPlayer());
		
		if (valueCardPokerPlayer1 > valueCardPokerPlayer2) {
			return new Ganador(getValueCardByIndex(valueCardPokerPlayer1), player1, TipoMano.POKER);
		}
		if (valueCardPokerPlayer1 < valueCardPokerPlayer2) {
			return new Ganador(getValueCardByIndex(valueCardPokerPlayer2), player2, TipoMano.POKER);
		}
		if(valueCardPokerPlayer1 == valueCardPokerPlayer2) {
			// TODO validacion desempate del poker
		}
		
		return null;
	}
	
	private Ganador validateStairColor(Jugador player1, Jugador player2) {
		
		final boolean sameColorPlayer1 = allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = allSameColor(player2.getCartasPlayer());
		
		final int beginStairPlayer1 = getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = getCorrectStair(player2.getCartasPlayer());
				
		if ((beginStairPlayer1 > beginStairPlayer2) && sameColorPlayer1) {
			return new Ganador(Carta.getNamePalo(player1), player1, TipoMano.ESCALERA_COLOR);
		}
		if ((beginStairPlayer1 < beginStairPlayer2) && sameColorPlayer2) {
			return new Ganador(Carta.getNamePalo(player2), player2, TipoMano.ESCALERA_COLOR);
		}
		if((beginStairPlayer1 == beginStairPlayer2) && (sameColorPlayer1 && sameColorPlayer2)) {
			// TODO validacion desempate de la escalera de color
		}
		
		return null;
	}
	
	private Ganador validateRealStair(Jugador player1, Jugador player2) {
		
		final boolean sameColorPlayer1 = allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = allSameColor(player2.getCartasPlayer());
		
		final int beginStairPlayer1 = getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = getCorrectStair(player2.getCartasPlayer());
		
		final boolean isFirstPositionTenPlayer1 = isFirstCardTen(beginStairPlayer1);
		final boolean isFirstPositionTenPlayer2 = isFirstCardTen(beginStairPlayer2);
				
		if((isFirstPositionTenPlayer1 && isFirstPositionTenPlayer2) && (sameColorPlayer1 && sameColorPlayer2)) {
			// TODO validacion desempate de la escalera de real
		}
		if (sameColorPlayer1 && isFirstPositionTenPlayer1) {
			return new Ganador(Carta.getNamePalo(player1), player1, TipoMano.ESCALERA_REAL);
		}
		if (sameColorPlayer2 && isFirstPositionTenPlayer2) {
			return new Ganador(Carta.getNamePalo(player2), player2, TipoMano.ESCALERA_REAL);
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
