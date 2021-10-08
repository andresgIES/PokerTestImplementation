import static constants.Constants.CARTAS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Carta;
import domain.Ganador;
import domain.Mano;
import domain.Poker;
import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

class PokerTest {
	
	private static final String VALOR_K = "K";
	private static final String VALOR_A = "A";
	private static final String VALOR_3 = "3";

    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C AH Negro gana. - con la carta alta: As ")
    @Test
    void testCartaAlta() throws ExceptionValidationPoker {
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 9C KD"), TipoMano.CARTA_ALTA);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S 8C AH"), TipoMano.CARTA_ALTA);

    	// when
    	final Ganador cartaGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then
    	assertEquals(VALOR_A, cartaGanadoraActual.getCartaGanadora());
    }

    // FIXME deberia ser carta alta contra par
    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C 2C Blanco gana. - con la carta alta: Rey ")
    @Test
    void testCartaAlta2() throws ExceptionValidationPoker {    	
    	
    	// given
    	final Poker juego = new Poker();
    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 9C KD "), TipoMano.CARTA_ALTA);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S 8C 2C"), TipoMano.CARTA_ALTA);

    	// when
    	final Ganador cartaGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then
    	assertEquals(VALOR_K, cartaGanadoraActual.getCartaGanadora());
    }
    
    @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    @Test
    void testCartaAlta3() {
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 8C"), TipoMano.CARTA_ALTA);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S AC AH"), null);
    	
    	// when
        Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
        	juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
        });

        // then
        String expectedMessage = ExceptionValidationPoker.HAND_INVALID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //par

    @DisplayName("Blanco: 2H 3D 5S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con par: Rey ")
    @Test
    void testPar() throws ExceptionValidationPoker {
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S KC KD"), TipoMano.PAR);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S 8C AH"), TipoMano.CARTA_ALTA);

    	// when
    	final Ganador cartaGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then
    	assertEquals(VALOR_K, cartaGanadoraActual.getCartaGanadora());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S AC AH Negro gana. - con par: As ")
    @Test
    void testPar2() throws ExceptionValidationPoker {
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 8C KD"), TipoMano.CARTA_ALTA);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S AC AH"), TipoMano.PAR);

    	// when
    	final Ganador cartaGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then
    	assertEquals(VALOR_A, cartaGanadoraActual.getCartaGanadora());

    }
    
    @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    @Test
    void testPar3() {
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 8C"), TipoMano.CARTA_ALTA);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S AC AH"), TipoMano.PAR);
    	
    	// when
        Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
        	juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
        });

        // then
        String expectedMessage = ExceptionValidationPoker.HAND_INVALID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //dos pares
    
    @DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con dos pares: Rey y Tres")
    @Test
    void testDosPares() throws ExceptionValidationPoker {
    	
    	// given
    	final Poker juego = new Poker();

    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 3S KC KD"), TipoMano.DOBLE_PAR);
    	    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 4S 8C AH"), TipoMano.DOBLE_PAR);

    	// when
    	final Ganador cartasGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then 
    	final String cartasExpected = VALOR_3.concat(", ").concat(VALOR_K);
    	assertEquals(cartasExpected, cartasGanadoraActual.getCartaGanadora());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 3S AC AH Negro gana. - con dos pares: As y Tres ")
    @Test
    void testDosPares2() throws ExceptionValidationPoker {    	
    	
    	// given
    	final Poker juego = new Poker();
    	    	
    	final Mano manoJugadorBlanco = new Mano(getCartasFromString("2H 3D 5S 8C KD"), TipoMano.DOBLE_PAR);
    	
    	final Mano manoJugadorNegro = new Mano(getCartasFromString("2C 3H 3S AC AH"), TipoMano.DOBLE_PAR);

    	// when
    	final Ganador cartasGanadoraActual = juego.getValidationbyManoMayor(manoJugadorBlanco, manoJugadorNegro);
    	
    	// then 
    	final String cartasExpected = VALOR_3.concat(", ").concat(VALOR_A);
    	assertEquals(cartasExpected, cartasGanadoraActual.getCartaGanadora());
    }
    
    private List<Carta> getCartasFromString (String cartas){
    	
    	List<Carta> listOfCartas = new ArrayList<>();
    	
        String data = cartas.trim();
        String[] split = data.split(" ");
        
        for (String keyCard : split) {
			listOfCartas.add(CARTAS.get(keyCard));
		}
           	
    	return listOfCartas;
    }

    /* terna

    @DisplayName("Blanco: 2H 3D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con Terna: Rey ")
    @Test
    void testTerna() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H AS AC AH Negro gana. - con Terna: As ")
    @Test
    void testTerna2() {
        fail("no implementado");
    }


    // escalera
    @DisplayName("Blanco: 2H 3D 4S 5C 6D  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera ")
    @Test
    void testEscalera() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S 5C 6H Negro gana. - con Escalera ")
    @Test
    void testEscalera2() {
        fail("no implementado");
    }


    // color
    @DisplayName("Blanco: 2H 3H 5H KH 8H  Negro: 2C 3H 4S 8C AH Blanco gana. - con color ")
    @Test
    void testColor() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C AC RC Negro gana. - con color ")
    @Test
    void testColor2() {
        fail("no implementado");
    }

    //full house
    @DisplayName("Blanco: 2H 2D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con full house ")
    @Test
    void testFullHouse() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 3S AC AH Negro gana. - con full house ")
    @Test
    void testFullHouse2() {
        fail("no implementado");
    }

    // poker
    @DisplayName("Blanco: 2H 2D 2S 2C KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con poker: 2 ")
    @Test
    void testPoker() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 2S 2D KH Negro gana. - con poker: 2 ")
    @Test
    void testPoker2() {
        fail("no implementado");
    }


    // escalera color
    @DisplayName("Blanco: 2H 3H 4H 5H 6H  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Color: Corazon")
    @Test
    void testEscaleraColor() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C 5C 6C Negro gana. - con Escalera Color: Trebol")
    @Test
    void testEscaleraColor2() {
        fail("no implementado");
    }


    // escalera real
    @DisplayName("Blanco: 10H JH QH KH AH  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Real: Corazon ")
    @Test
    void testEscaleraReal() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 10C JC QC KC AC Negro gana. - con Escalera Real: Trebol")
    @Test
    void testEscaleraReal2() {
        fail("no implementado");
    }
    
    */
}