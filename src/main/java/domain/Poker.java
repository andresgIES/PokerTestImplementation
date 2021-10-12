package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.getValueCardByIndex;
import static constants.Constants.isNotIndexNotFound;

import java.util.List;
import java.util.stream.Collectors;

import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
				
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

	private int validateMayorHand(Mano handPlayer1, Mano handPlayer2) {
		
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
		
		final int valueHighCardPlayer1 = PokerValidations.getValueHighCard(handPlayer1.getCartas());
		final int valueHighCardPlayer2 = PokerValidations.getValueHighCard(handPlayer2.getCartas());
		
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
		
		final int valueParPlayer1 = PokerValidations.getValueCardPar(handPlayer1.getCartas());
		final int valueParPlayer2 = PokerValidations.getValueCardPar(handPlayer2.getCartas());
				
		if((valueParPlayer1 > valueParPlayer2) && isNotIndexNotFound(valueParPlayer1)) {
			return new Ganador(getValueCardByIndex(valueParPlayer1), TipoMano.PAR);
		}
		if((valueParPlayer1 < valueParPlayer2) && isNotIndexNotFound(valueParPlayer2)) {
			return new Ganador(getValueCardByIndex(valueParPlayer2), TipoMano.PAR);
		}
		if(valueParPlayer1 == valueParPlayer2) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}

		return null;
	}
	
	private List<Carta> getNewListWithOutSpecificCard(List<Carta> playerCards, final int cardToRemove) {
		if(!isNotIndexNotFound(cardToRemove)) {
			return playerCards;
		}
		else {
			playerCards = playerCards.stream().filter(carta -> 
			!carta.getValor().equals(getValueCardByIndex(cardToRemove)))
			.collect(Collectors.toList());
		}
		return playerCards;
	}
	
	private Ganador validateDoblePar(Mano handPlayer1, Mano handPlayer2) {
				
		final int firstParPlayer1 = PokerValidations.getValueCardPar(handPlayer1.getCartas());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(handPlayer1.getCartas(), firstParPlayer1);
		final int secondParPlayer1 = PokerValidations.getValueCardPar(cardsPlayer1Filtred);
		
		final int firstParPlayer2 = PokerValidations.getValueCardPar(handPlayer2.getCartas());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(handPlayer2.getCartas(), firstParPlayer2);
		final int secondParPlayer2 = PokerValidations.getValueCardPar(cardsPlayer2Filtred);
		
		final int bestDoblePairPlayer1 = (firstParPlayer1 > secondParPlayer1) ? firstParPlayer1 : secondParPlayer1;
		final int bestDoblePairPlayer2 = (firstParPlayer2 > secondParPlayer2) ? firstParPlayer2 : secondParPlayer2;
		
		if (bestDoblePairPlayer1 == bestDoblePairPlayer2){
			// TODO implementar empate de doble par
			return new Ganador();
		}

		if ((bestDoblePairPlayer1 > bestDoblePairPlayer2) && PokerValidations.validateBothIndex(firstParPlayer1, secondParPlayer1)) {
			final String winnerCards = 
					getValueCardByIndex(firstParPlayer1).concat(", ").concat(getValueCardByIndex(secondParPlayer1));
			return new Ganador(winnerCards, TipoMano.DOBLE_PAR);
		}
		
		if (bestDoblePairPlayer1 < bestDoblePairPlayer2 && PokerValidations.validateBothIndex(firstParPlayer2, secondParPlayer2)) {
			final String winnerCards = 
					getValueCardByIndex(firstParPlayer2).concat(", ").concat(getValueCardByIndex(secondParPlayer2));
			return new Ganador(winnerCards, TipoMano.DOBLE_PAR);
		}
		
		return null;
	}
		
	private Ganador validateTerna (Mano handPlayer1, Mano handPlayer2) {
		
		final int valueTernaCardPlayer1 = PokerValidations.getValueTerna(handPlayer1.getCartas());
		final int valueTernaCardPlayer2 = PokerValidations.getValueTerna(handPlayer2.getCartas());
		
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
		
		final int beginStairPlayer1 = PokerValidations.getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = PokerValidations.getCorrectStair(handPlayer2.getCartas());
		
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
		
		final boolean sameColorPlayer1 = PokerValidations.allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = PokerValidations.allSameColor(handPlayer2.getCartas());
				
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

		final int valueTernaPlayer1 = PokerValidations.getValueTerna(handPlayer1.getCartas());
		final int valueTernaPlayer2 = PokerValidations.getValueTerna(handPlayer2.getCartas());

		if(valueTernaPlayer1 > valueTernaPlayer2 && isNotIndexNotFound(valueTernaPlayer1)) {
			return new Ganador(getValueCardByIndex(valueTernaPlayer1), TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 < valueTernaPlayer2 && isNotIndexNotFound(valueTernaPlayer2)) {
			return new Ganador(getValueCardByIndex(valueTernaPlayer2), TipoMano.FULL_HOUSE);
		}
		if(valueTernaPlayer1 == valueTernaPlayer2 && PokerValidations.validateBothIndex(valueTernaPlayer1, valueTernaPlayer2)) {
			// TODO implementar desempate full house
		}

		return null;
	}
	
	private Ganador validatePoker(Mano handPlayer1, Mano handPlayer2) {
		
		final int valueCardPokerPlayer1 = PokerValidations.getValuePoker(handPlayer1.getCartas());
		final int valueCardPokerPlayer2 = PokerValidations.getValuePoker(handPlayer2.getCartas());
		
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
		
		final boolean sameColorPlayer1 = PokerValidations.allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = PokerValidations.allSameColor(handPlayer2.getCartas());
		
		final int beginStairPlayer1 = PokerValidations.getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = PokerValidations.getCorrectStair(handPlayer2.getCartas());
		
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
		
		final boolean sameColorPlayer1 = PokerValidations.allSameColor(handPlayer1.getCartas());
		final boolean sameColorPlayer2 = PokerValidations.allSameColor(handPlayer2.getCartas());
		
		final int beginStairPlayer1 = PokerValidations.getCorrectStair(handPlayer1.getCartas());
		final int beginStairPlayer2 = PokerValidations.getCorrectStair(handPlayer2.getCartas());
		
		final boolean isFirstPositionTenPlayer1 = PokerValidations.isFirstCardTen(beginStairPlayer1);
		final boolean isFirstPositionTenPlayer2 = PokerValidations.isFirstCardTen(beginStairPlayer2);
		
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
	
		
}
