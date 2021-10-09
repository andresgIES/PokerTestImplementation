package domain;

import static constants.Constants.SIZE_HAND;
import static constants.Constants.getValueCardByIndex;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

public class Poker {
	
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
		
		final String valueParPlayer1 = PokerValidations.getValueCardPar(handPlayer1.getCartas());
		final String valueParPlayer2 = PokerValidations.getValueCardPar(handPlayer2.getCartas());
		
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
	
	private List<Carta> getNewListWithOutSpecificCard(List<Carta> playerCards, final Carta cardToRemove) {
		if(Objects.nonNull(cardToRemove)) {
			playerCards = playerCards.stream().filter(carta -> 
			!carta.getValor().equals(cardToRemove.getValor()))
			.collect(Collectors.toList());
		}
		return playerCards;
	}
	
	private Ganador validateDoblePar(Mano handPlayer1, Mano handPlayer2) {
				
		final Carta firstParPlayer1 = PokerValidations.getCartaDoublePair(handPlayer1.getCartas());
		final List<Carta> cardsPlayer1Filtred = getNewListWithOutSpecificCard(handPlayer1.getCartas(), firstParPlayer1);
		final Carta secondParPlayer1 = PokerValidations.getCartaDoublePair(cardsPlayer1Filtred);
		
		final Carta firstParPlayer2 = PokerValidations.getCartaDoublePair(handPlayer2.getCartas());
		final List<Carta> cardsPlayer2Filtred = getNewListWithOutSpecificCard(handPlayer2.getCartas(), firstParPlayer2);
		final Carta segundoParJugador2 = PokerValidations.getCartaDoublePair(cardsPlayer2Filtred);
		
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
		
	private Ganador validateTerna (Mano handPlayer1, Mano handPlaye1) {
		
		final int valueTernaCardPlayer1 = PokerValidations.getValueTerna(handPlayer1.getCartas());
		final int valueTernaCardPlayer2 = PokerValidations.getValueTerna(handPlaye1.getCartas());
		
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
	
		
	private Ganador validateStair(Mano handPlayer1, Mano handPlayer2) throws ExceptionValidationPoker {
		
		final int beginStairPlayer1 = PokerValidations.getCorrectSequence(handPlayer1.getCartas());
		final int beginStairPlayer2 = PokerValidations.getCorrectSequence(handPlayer2.getCartas());
		
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
	
		
}
