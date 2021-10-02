import org.junit.jupiter.api.Test;

import domain.Carta;
import domain.Ganador;
import domain.Mano;
import domain.Poker;
import enums.Palo;
import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;

class PokerTest {
	
	private static final String VALOR_2 = "2";
	private static final String VALOR_3 = "3";
	private static final String VALOR_5 = "5";
	private static final String VALOR_K = "K";
	private static final String VALOR_4 = "4";
	private static final String VALOR_8 = "8";
	private static final String VALOR_A = "A";

    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C AH Negro gana. - con la carta alta: As ")
    @Test
    void testCartaAlta() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C 2C Blanco gana. - con la carta alta: Rey ")
    @Test
    void testCartaAlta2() {
        fail("no implementado");
    }

    //par

    @DisplayName("Blanco: 2H 3D 5S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con par: Rey ")
    @Test
    void testPar() throws ExceptionValidationPoker {
    	
    	final Poker juego = new Poker();
    	
    	final Carta h2 = new Carta(Palo.H, VALOR_2);
    	final Carta d3 = new Carta(Palo.D, VALOR_3); 
    	final Carta s5 = new Carta(Palo.S, VALOR_5); 
    	final Carta cK = new Carta(Palo.C, VALOR_K); 
    	final Carta dK = new Carta(Palo.D, VALOR_K); 
    	
    	final Mano manoJugadorBlanco = new Mano(Arrays.asList(h2, d3, s5, cK, dK), TipoMano.PAR);
    	
    	final Carta c2 = new Carta(Palo.C, VALOR_2);
    	final Carta h3 = new Carta(Palo.H, VALOR_3); 
    	final Carta s4 = new Carta(Palo.S, VALOR_4); 
    	final Carta c8 = new Carta(Palo.C, VALOR_8); 
    	final Carta hA = new Carta(Palo.H, VALOR_A); 
    	
    	final Mano manoJugadorNegro = new Mano(Arrays.asList(c2, h3, s4, c8, hA), TipoMano.PAR);

    	final Ganador cartaGanadoraActual = juego.validatePar(manoJugadorBlanco, manoJugadorNegro);
    	
    	assertEquals(VALOR_K, cartaGanadoraActual.getCartaGanadora());
    	
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S AC AH Negro gana. - con par: As ")
    @Test
    void testPar2() throws ExceptionValidationPoker {
    	
    	final Poker juego = new Poker();
    	
    	final Carta h2 = new Carta(Palo.H, VALOR_2);
    	final Carta d3 = new Carta(Palo.D, VALOR_3); 
    	final Carta s5 = new Carta(Palo.S, VALOR_5); 
    	final Carta c8 = new Carta(Palo.C, VALOR_8); 
    	final Carta dK = new Carta(Palo.D, VALOR_K); 
    	
    	final Mano manoJugadorBlanco = new Mano(Arrays.asList(h2, d3, s5, c8, dK), TipoMano.PAR);
    	
    	final Carta c2 = new Carta(Palo.C, VALOR_2);
    	final Carta h3 = new Carta(Palo.H, VALOR_3); 
    	final Carta s4 = new Carta(Palo.S, VALOR_4); 
    	final Carta cA = new Carta(Palo.C, VALOR_A); 
    	final Carta hA = new Carta(Palo.H, VALOR_A); 
    	
    	final Mano manoJugadorNegro = new Mano(Arrays.asList(c2, h3, s4, cA, hA), TipoMano.PAR);

    	final Ganador cartaGanadoraActual = juego.validatePar(manoJugadorBlanco, manoJugadorNegro);
    	
    	assertEquals(VALOR_A, cartaGanadoraActual.getCartaGanadora());

    }
    
    @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    @Test
    void testPar3() throws ExceptionValidationPoker {
    	
    	final Poker juego = new Poker();
    	
    	final Carta h2 = new Carta(Palo.H, VALOR_2);
    	final Carta d3 = new Carta(Palo.D, VALOR_3); 
    	final Carta s5 = new Carta(Palo.S, VALOR_5); 
    	final Carta c8 = new Carta(Palo.C, VALOR_8); 
    	
    	final Mano manoJugadorBlanco = new Mano(Arrays.asList(h2, d3, s5, c8), TipoMano.PAR);
    	
    	final Carta c2 = new Carta(Palo.C, VALOR_2);
    	final Carta h3 = new Carta(Palo.H, VALOR_3); 
    	final Carta s4 = new Carta(Palo.S, VALOR_4); 
    	final Carta cA = new Carta(Palo.C, VALOR_A); 
    	final Carta hA = new Carta(Palo.H, VALOR_A); 
    	
    	final Mano manoJugadorNegro = new Mano(Arrays.asList(c2, h3, s4, cA, hA), TipoMano.PAR);
    	
        Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
        	juego.validatePar(manoJugadorBlanco, manoJugadorNegro);
        });

        String expectedMessage = ExceptionValidationPoker.HAND_INVALID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //dos pares


    @DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con dos pares: Rey y Tres")
    @Test
    void testDosPares() {
        fail("no implementado");
    }

    @DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 3S AC AH Negro gana. - con dos pares: As y Tres ")
    @Test
    void testDosPares2() {
        fail("no implementado");
    }

    // terna

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
}