package domain;

import static constants.Constants.CARTAS;
import static constants.Constants.INDEX_NOT_FOUND;
import static constants.Constants.getIndexCardByValue;
import static constants.Constants.getNewListWithOutSpecificCard;
import static constants.Constants.getValueCardByIndex;
import static constants.Constants.isNotIndexNotFound;
import static constants.Constants.validateTwoIndexDifferentNotFound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.Palo;
import enums.TipoMano;

public class Mano {

	private static final int PAR = 2;
	private static final int TERNA = 3;
	private static final int POKER = 4;
	private static final int INDICE_CARTA_10 = 8;

	private List<Carta> cartas;

	private TipoMano tipoDeMano;

	public Mano() {
		super();
	}

	public Mano(String cartas) {

		List<Carta> listOfCartas = new ArrayList<>();

		String data = cartas.trim();
		String[] split = data.split(" ");

		for (String keyCard : split) {
			listOfCartas.add(CARTAS.get(keyCard));
		}

		this.cartas = listOfCartas;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public TipoMano getTipoDeMano() {
		return tipoDeMano;
	}

	public void setTipoDeMano(TipoMano tipoDeMano) {
		this.tipoDeMano = tipoDeMano;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public int getValorMano() {
		return this.getTipoDeMano().getValor();
	}

	public Carta getCartaByIndex(int indexCard) {
		return this.getCartas().get(indexCard);
	}
	
	public Mano setTipoMano(TipoMano tipoDeMano) {
		this.tipoDeMano = tipoDeMano;
		return this;
	}

	public Mano getTypeOfHand() {

		final boolean sameColor = allSameColor(this.getCartas());
		final int beginStair = getCorrectStair(this.getCartas());
		final boolean isFirstPositionTen = isFirstCardTen(beginStair);
		final int valueCardPoker = getValuePoker(this.getCartas());

		if (sameColor && isFirstPositionTen) {
			return this.setTipoMano(TipoMano.ESCALERA_REAL);
		}

		if (sameColor && isNotIndexNotFound(beginStair)) {
			return this.setTipoMano(TipoMano.ESCALERA_COLOR);
		}

		if (isNotIndexNotFound(valueCardPoker)) {
			return this.setTipoMano(TipoMano.POKER);
		}

		final int valueTerna = getValueTerna(this.getCartas());
		final List<Carta> cardsPlayerFiltred = getNewListWithOutSpecificCard(this.getCartas(), valueTerna);
		final int parPlayer = getValueCardPar(cardsPlayerFiltred);

		if (validateTwoIndexDifferentNotFound(valueTerna, parPlayer)) {
			return this.setTipoMano(TipoMano.FULL_HOUSE);
		}

		if (sameColor) {
			return this.setTipoMano(TipoMano.COLOR);
		}

		if (isNotIndexNotFound(beginStair)) {
			return this.setTipoMano(TipoMano.ESCALERA);
		}

		if (isNotIndexNotFound(valueTerna)) {
			return this.setTipoMano(TipoMano.TERNA);
		}

		final int firstPar = getValueCardPar(this.getCartas());
		final List<Carta> cardsParFiltred = getNewListWithOutSpecificCard(this.getCartas(), firstPar);
		final int secondPar = getValueCardPar(cardsParFiltred);

		if (validateTwoIndexDifferentNotFound(firstPar, secondPar)) {
			return this.setTipoMano(TipoMano.DOBLE_PAR);
		}

		final int valuePar = getValueCardPar(this.getCartas());

		if (isNotIndexNotFound(valuePar)) {
			return this.setTipoMano(TipoMano.PAR);
		}

		final int valueHighCard = getValueHighCard(this.getCartas());

		if (isNotIndexNotFound(valueHighCard)) {
			return this.setTipoMano(TipoMano.CARTA_ALTA);
		}

		return this;
	}

	public static int getValueHighCard(final List<Carta> cards) {

		int maxValue = 0;

		for (Carta card : cards) {
			int valueIndexOfCard = getIndexCardByValue(card.getValor());
			if (valueIndexOfCard >= maxValue) {
				maxValue = valueIndexOfCard;
			}
		}
		return maxValue;
	}

	public static int getValueCardPar(final List<Carta> cards) {
		return getValueEqualCard(cards, PAR);
	}

	public static int getValueTerna(final List<Carta> cartas) {
		return getValueEqualCard(cartas, TERNA);
	}

	public static int getCorrectStair(final List<Carta> hand) {

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

	public static boolean allSameColor(final List<Carta> cards) {

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

	public static int getValuePoker(final List<Carta> cards) {
		return getValueEqualCard(cards, POKER);
	}

	public static boolean isFirstCardTen(int valueFirstPosition) {
		return INDICE_CARTA_10 == valueFirstPosition;
	}

	public static int getValueEqualCard(final List<Carta> cards, int repetitions) {

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

	@Override
	public String toString() {
		return "Mano [cartas=" + cartas + ", tipoDeMano=" + tipoDeMano + "]";
	}

}
