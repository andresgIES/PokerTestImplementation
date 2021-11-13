import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Ganador;
import domain.Mano;
import domain.Poker;
import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

class PokerTest {
	
	private static final String VALOR_K = "K";
	private static final String VALOR_A = "A";
	private static final String VALOR_3 = "3";
	private static final String VALOR_2 = "2";
	
	private Poker juego = null; 
	
	@BeforeEach
	public void initPoker(){
		juego = new Poker();
	}

    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C AH Negro gana. - con la carta alta: As ")
    @Test
    void testCartaAlta() throws ExceptionValidationPoker {
    	
    	// given    	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 9C KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.CARTA_ALTA;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }

    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C 2C Blanco gana. - con par de : 2 ")
    @Test
    void testCartaAlta2() throws ExceptionValidationPoker {    	
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 9C KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C 2C");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.PAR;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }
    
    @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    @Test
    void testCartaAlta3() {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S AC AH");
    	
    	// when
        Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
        	juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
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
    	final Mano manoJugadorBlanco = new Mano("2H 3D 5S KC KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(manoJugadorBlanco, handPlayerBlack);
    	
    	// then
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.PAR;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S AC AH Negro gana. - con par: As ")
    @Test
    void testPar2() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S AC AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.PAR;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());

    }
    
    @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    @Test
    void testPar3() {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S AC AH");
    	
    	// when
        Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
        	juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
        });

        // then
        String expectedMessage = ExceptionValidationPoker.HAND_INVALID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @DisplayName("Blanco: 2H 2D 5S 8C KD  Negro: 2C 2H 4S JC AH Negro gana. - con carta alta As")
    @Test
    void testPar4() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 2D 5S 8C KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 2H 4S JC AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.PAR;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());

    }

    //dos pares
    
    @DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con dos pares: Rey y Tres")
    @Test
    void testDosPares() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 3S KC KD");
    	    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String cartasExpected = VALOR_3.concat(", ").concat(VALOR_K);
    	assertEquals(cartasExpected, actualCardsWinner.getCartaGanadora());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 3S AC AH Negro gana. - con dos pares: As y Tres ")
    @Test
    void testDosPares2() throws ExceptionValidationPoker {    	
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 3S AC AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String cartasExpected = VALOR_3.concat(", ").concat(VALOR_A);
    	assertEquals(cartasExpected, actualCardsWinner.getCartaGanadora());
    }
    
    @DisplayName("Blanco: 3H 3D 5S 5C KD  Negro: 2C 3H 3S AC AH Negro gana. - empate de dos pares: gana As y Tres ")
    @Test
    void testDosPares3() throws ExceptionValidationPoker {    	
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("3H 3D 5S 5C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 3S AC AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String cartasExpected = VALOR_3.concat(", ").concat(VALOR_A);
    	assertEquals(cartasExpected, actualCardsWinner.getCartaGanadora());
    }
    
    // terna

    @DisplayName("Blanco: 2H 3D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con Terna: Rey ")
    @Test
    void testTerna() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D KS KC KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.TERNA;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H AS AC AH Negro gana. - con Terna: As ")
    @Test
    void testTerna2() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H AS AC AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.TERNA;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }


    // escalera
    @DisplayName("Blanco: 2H 3D 4S 5C 6D  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera ")
    @Test
    void testEscalera() throws ExceptionValidationPoker {    	
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3D 4S 5C 6D");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.ESCALERA;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
 
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S 5C 6H Negro gana. - con Escalera ")
    @Test
    void testEscalera2() throws ExceptionValidationPoker {

    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 5C 6H");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.ESCALERA;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }


    // color
    @DisplayName("Blanco: 2H 3H 5H KH 8H  Negro: 2C 3H 4S 8C AH Blanco gana. - con color ")
    @Test
    void testColor() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 3H 5H KH 8H");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana el palo: Corazones";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C AC QC Negro gana. - con color ")
    @Test
    void testColor2() throws ExceptionValidationPoker {
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3C 4C AC QC");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana el palo: Treboles";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());
    }

    // full house
    @DisplayName("Blanco: 2H 2D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con full house ")
    @Test
    void testFullHouse() throws ExceptionValidationPoker {
    	
    	// given
    	final Mano handPlayerWhite = new Mano("2H 2D KS KC KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String cartasExpected = VALOR_K.concat(", ").concat(VALOR_2);
    	assertEquals(cartasExpected, actualCardsWinner.getCartaGanadora());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 2S AC AH Negro gana. - con full house ")
    @Test
    void testFullHouse2() throws ExceptionValidationPoker {
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 2H 2S AC AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String cartasExpected = VALOR_2.concat(", ").concat(VALOR_A);
    	assertEquals(cartasExpected, actualCardsWinner.getCartaGanadora());
    }

    // poker
    @DisplayName("Blanco: 2H 2D 2S 2C KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con poker: 2 ")
    @Test
    void testPoker() throws ExceptionValidationPoker {
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 2D 2S 2C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.POKER;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 2S 2D KH Negro gana. - con poker: 2 ")
    @Test
    void testPoker2() throws ExceptionValidationPoker {  
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 2H 2S 2D KH");

    	// when
    	final Ganador actualCardWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String messageExpected = "El ganador es el jugador: "+actualCardWinner.getNombreJugador()+" Con la mano de tipo: "+TipoMano.POKER;
    	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    }


    // escalera color
    @DisplayName("Blanco: 2H 3H 4H 5H 6H  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Color: Corazon")
    @Test
    void testEscaleraColor() throws ExceptionValidationPoker {
    	
    	// given 	
    	final Mano handPlayerWhite = new Mano("2H 3H 4H 5H 6H");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana la escalera de: Corazones";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());   
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C 5C 6C Negro gana. - con Escalera Color: Trebol")
    @Test
    void testEscaleraColor2() throws ExceptionValidationPoker {    
    	
    	// given  	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("2C 3C 4C 5C 6C");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana la escalera de: Treboles";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());    
    	
    }


    // escalera real
    @DisplayName("Blanco: 10H JH QH KH AH  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Real: Corazon ")
    @Test
    void testEscaleraReal() throws ExceptionValidationPoker {   
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("10H JH QH KH AH");
    	
    	final Mano handPlayerBlack = new Mano("2C 3H 4S 8C AH");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana la escalera real de: Corazones";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 10C JC QC KC AC Negro gana. - con Escalera Real: Trebol")
    @Test
    void testEscaleraReal2() throws ExceptionValidationPoker {    	
    	
    	// given	
    	final Mano handPlayerWhite = new Mano("2H 3D 5S 8C KD");
    	
    	final Mano handPlayerBlack = new Mano("10C JC QC KC AC");

    	// when
    	final Ganador actualCardsWinner = juego.getValidationByMayorHand(handPlayerWhite, handPlayerBlack);
    	
    	// then 
    	final String expectedMessageWinner = "Gana la escalera real de: Treboles";
    	assertEquals(expectedMessageWinner, actualCardsWinner.getCartaGanadora());
    }
    
}