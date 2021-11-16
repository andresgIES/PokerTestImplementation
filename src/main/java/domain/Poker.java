package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.getNewListWithOutSpecificCard;
import static constants.Constants.getValueCardByIndex;
import static constants.Constants.isNotIndexNotFound;
import static constants.Constants.validateTwoIndexDifferentNotFound;

import java.util.List;

import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
	

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
		
		final Mano playerHand1 = player1.getTypeOfHand();
		final Mano playerHand2 = player2.getTypeOfHand();
		
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
		
		final int valueHighCardPlayer1 = Mano.getValueHighCard(player1.getCartasPlayer());
		final int valueHighCardPlayer2 = Mano.getValueHighCard(player2.getCartasPlayer());
		
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
		
		final int valueParPlayer1 = Mano.getValueCardPar(player1.getCartasPlayer());
		final int valueParPlayer2 = Mano.getValueCardPar(player2.getCartasPlayer());
				
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
		
	private Ganador validateDoblePar(Jugador player1, Jugador player2) {
						
		final int firstParPlayer1 = Mano.getValueCardPar(player1.getCartasPlayer());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(player1.getCartasPlayer(), firstParPlayer1);
		final int secondParPlayer1 = Mano.getValueCardPar(cardsPlayer1Filtred);
		
		final int firstParPlayer2 = Mano.getValueCardPar(player2.getCartasPlayer());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(player2.getCartasPlayer(), firstParPlayer2);
		final int secondParPlayer2 = Mano.getValueCardPar(cardsPlayer2Filtred);
		
		final int bestDoblePairPlayer1 = (firstParPlayer1 > secondParPlayer1) ? firstParPlayer1 : secondParPlayer1;
		final int bestDoblePairPlayer2 = (firstParPlayer2 > secondParPlayer2) ? firstParPlayer2 : secondParPlayer2;
		
		if (bestDoblePairPlayer1 == bestDoblePairPlayer2 && validateTwoIndexDifferentNotFound(bestDoblePairPlayer1, bestDoblePairPlayer2)){
			
			final int secondDoblePairPlayer1 = (firstParPlayer1 == bestDoblePairPlayer1) ? secondParPlayer1 : firstParPlayer1;
			final int secondDoblePairPlayer2 = (firstParPlayer2 == bestDoblePairPlayer2) ? secondParPlayer2 : firstParPlayer2;

			if ((secondDoblePairPlayer1 > secondDoblePairPlayer2) && validateTwoIndexDifferentNotFound(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return Ganador.getWinnerWith2Cards(
						getValueCardByIndex(firstParPlayer1), getValueCardByIndex(secondParPlayer1), player1, TipoMano.DOBLE_PAR);
			}
			
			if ((secondDoblePairPlayer2 > secondDoblePairPlayer1) && validateTwoIndexDifferentNotFound(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				return Ganador.getWinnerWith2Cards(
						getValueCardByIndex(firstParPlayer2), getValueCardByIndex(secondParPlayer2), player2, TipoMano.DOBLE_PAR);
			}
			
			if (secondDoblePairPlayer1 == secondDoblePairPlayer2 && validateTwoIndexDifferentNotFound(secondDoblePairPlayer1, secondDoblePairPlayer2)) {
				final List<Carta> cardsPlayer1SecondFilter = getNewListWithOutSpecificCard(cardsPlayer1Filtred, secondParPlayer1);
				final List<Carta> cardsPlayer2SecondFilter = getNewListWithOutSpecificCard(cardsPlayer2Filtred, secondParPlayer2);

				player1.getMano().setCartas(cardsPlayer1SecondFilter);
				player2.getMano().setCartas(cardsPlayer2SecondFilter);
				
				return validateHighCard(player1, player2);
			}
			
		}

		if ((bestDoblePairPlayer1 > bestDoblePairPlayer2) && validateTwoIndexDifferentNotFound(firstParPlayer1, secondParPlayer1)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(firstParPlayer1),getValueCardByIndex(secondParPlayer1), player1, TipoMano.DOBLE_PAR);
		}
		
		if (bestDoblePairPlayer1 < bestDoblePairPlayer2 && validateTwoIndexDifferentNotFound(firstParPlayer2, secondParPlayer2)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(firstParPlayer2), getValueCardByIndex(secondParPlayer2), player2, TipoMano.DOBLE_PAR);
		}
		
		return null;
	}
	
		
	private Ganador validateTerna (Jugador player1, Jugador player2) {
		
		final int valueTernaCardPlayer1 = Mano.getValueTerna(player1.getCartasPlayer());
		final int valueTernaCardPlayer2 = Mano.getValueTerna(player2.getCartasPlayer());
		
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
		
		final int beginStairPlayer1 = Mano.getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = Mano.getCorrectStair(player2.getCartasPlayer());
		
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
		
		final boolean sameColorPlayer1 = Mano.allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = Mano.allSameColor(player2.getCartasPlayer());
								
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

		final int valueTernaPlayer1 = Mano.getValueTerna(player1.getCartasPlayer());
		final int valueTernaPlayer2 = Mano.getValueTerna(player2.getCartasPlayer());
		
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(player1.getCartasPlayer(), valueTernaPlayer1);
		final int parPlayer1 = Mano.getValueCardPar(cardsPlayer1Filtred);
		
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(player2.getCartasPlayer(), valueTernaPlayer2);
		final int parPlayer2 = Mano.getValueCardPar(cardsPlayer2Filtred);

		if(valueTernaPlayer1 > valueTernaPlayer2 && validateTwoIndexDifferentNotFound(valueTernaPlayer1, parPlayer1)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(valueTernaPlayer1), getValueCardByIndex(parPlayer1), player1, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 < valueTernaPlayer2 && validateTwoIndexDifferentNotFound(valueTernaPlayer2, parPlayer2)) {
			return Ganador.getWinnerWith2Cards(
					getValueCardByIndex(valueTernaPlayer2), getValueCardByIndex(parPlayer2), player2, TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 == valueTernaPlayer2 && validateTwoIndexDifferentNotFound(valueTernaPlayer1, valueTernaPlayer2)
				&& validateTwoIndexDifferentNotFound(parPlayer1, parPlayer2)) {
			// TODO implementar desempate full house
		}

		return null;
	}
	
	private Ganador validatePoker(Jugador player1, Jugador player2) {
		
		final int valueCardPokerPlayer1 = Mano.getValuePoker(player1.getCartasPlayer());
		final int valueCardPokerPlayer2 = Mano.getValuePoker(player2.getCartasPlayer());
		
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
		
		final boolean sameColorPlayer1 = Mano.allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = Mano.allSameColor(player2.getCartasPlayer());
		
		final int beginStairPlayer1 = Mano.getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = Mano.getCorrectStair(player2.getCartasPlayer());
				
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
		
		final boolean sameColorPlayer1 = Mano.allSameColor(player1.getCartasPlayer());
		final boolean sameColorPlayer2 = Mano.allSameColor(player2.getCartasPlayer());
		
		final int beginStairPlayer1 = Mano.getCorrectStair(player1.getCartasPlayer());
		final int beginStairPlayer2 = Mano.getCorrectStair(player2.getCartasPlayer());
		
		final boolean isFirstPositionTenPlayer1 = Mano.isFirstCardTen(beginStairPlayer1);
		final boolean isFirstPositionTenPlayer2 = Mano.isFirstCardTen(beginStairPlayer2);
				
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
		
}
