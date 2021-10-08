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
		
		String valorCartaMano = "";
		
		for (Carta carta : cartas) {
			
			final String valorCarta = carta.getValor();
			int valoresIguales = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valorCarta)) {
					valoresIguales++;
				}
			}
			
			if(valoresIguales == PAR) {
				valorCartaMano = valorCarta;
			}
			
		}
		return valorCartaMano;
	}
	
	private Ganador validatePar(Mano manoJugador1, Mano manoJugador2){
		
		final String valorParJugador1 = getValorCartaPar(manoJugador1.getCartas());
		final String valorParJugador2 = getValorCartaPar(manoJugador2.getCartas());
		
		if(valorParJugador1.isEmpty()) {
			return new Ganador(valorParJugador2, TipoMano.PAR);
		}
		if(valorParJugador2.isEmpty()) {
			return new Ganador(valorParJugador1, TipoMano.PAR);
		}
		if(valorParJugador1.equals(valorParJugador2)) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}

		return null;
	}
	
	private int getValorCartaAlta(List<Carta> cartas) {
		int valorMaximo = 0;

		for (Carta card : cartas) {
			int valorCartaInt = VALORES.indexOf(card.getValor());
			if (valorCartaInt >= valorMaximo) {
				valorMaximo = valorCartaInt;
			}

		}
		return valorMaximo;
	}
	
	
	private Ganador validateCartaAlta(Mano manoJugador1, Mano manoJugador2) {
		
		final int valorCartaAltaJugador1 = getValorCartaAlta(manoJugador1.getCartas());
		final int valorCartaAltaJugador2 = getValorCartaAlta(manoJugador2.getCartas());
		
		if(valorCartaAltaJugador1 > valorCartaAltaJugador2) {
			return new Ganador(getValorCartaByIndex(valorCartaAltaJugador1), TipoMano.CARTA_ALTA);
		}
		if(valorCartaAltaJugador2 > valorCartaAltaJugador1) {
			return new Ganador(getValorCartaByIndex(valorCartaAltaJugador2), TipoMano.CARTA_ALTA);
		}
		if(valorCartaAltaJugador2 == valorCartaAltaJugador1) {
			// TODO implementar desempate de acuerdo a las demas cartas
		}
		
		return null;
	}
	
	private Carta getCartaDoblePar(List<Carta> cartas) {
		
		Carta valorCartaMano = null;
		
		for (Carta carta : cartas) {
			
			final String valorCarta = carta.getValor();
			int valoresIguales = 0;
			
			for (Carta card : cartas) {
				if(card.getValor().equals(valorCarta)) {
					valoresIguales++;
				}
			}
			
			if(valoresIguales == PAR) {
				valorCartaMano = carta;
				break;
			}
			
		}
		return valorCartaMano;
	}
	
	private List<Carta> getNewListWithOutSpecificCard(List<Carta> cartasJugador, final Carta valorCarta) {
		if(Objects.nonNull(valorCarta)) {
			cartasJugador = cartasJugador.stream().filter(carta -> 
			!carta.getValor().equals(valorCarta.getValor()))
			.collect(Collectors.toList());
		}
		return cartasJugador;
	}
	
	private Ganador validateDoblePar(Mano manoJugador1, Mano manoJugador2) {
				
		final Carta primerParJugador1 = getCartaDoblePar(manoJugador1.getCartas());
		
		List<Carta> cartasJugador1 = getNewListWithOutSpecificCard(manoJugador1.getCartas(), primerParJugador1);
		
		final Carta segundoParJugador1 = getCartaDoblePar(cartasJugador1);
		

						
		final Carta primerParJugador2 = getCartaDoblePar(manoJugador2.getCartas());
		
		List<Carta> cartasJugador2 = getNewListWithOutSpecificCard(manoJugador2.getCartas(), primerParJugador2);

		final Carta segundoParJugador2 = getCartaDoblePar(cartasJugador2);
		
		
		final boolean noNullParesJugador1 = Objects.nonNull(primerParJugador1) && Objects.nonNull(segundoParJugador1);
		
		final boolean noNullParesJugador2 = Objects.nonNull(primerParJugador2) && Objects.nonNull(segundoParJugador2);

		if (noNullParesJugador1 && !noNullParesJugador2) {
			final String cartaGanadora = primerParJugador1.getValor().concat(", ").concat(segundoParJugador1.getValor());
			return new Ganador(cartaGanadora, TipoMano.DOBLE_PAR);
		}
		
		if (!noNullParesJugador1 && noNullParesJugador2) {
			final String cartaGanadora = primerParJugador2.getValor().concat(", ").concat(segundoParJugador2.getValor());
			return new Ganador(cartaGanadora, TipoMano.DOBLE_PAR);
		}
		
		if (noNullParesJugador1 && noNullParesJugador2){
			// TODO implementar empate de doble par
		}
				
		return new Ganador();
	}
	
	private String getValorCartaByIndex(int index) {
		return VALORES.get(index);
	}


		
}
