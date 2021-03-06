import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domain.Ganador;
import domain.Jugador;
import domain.Mano;
import domain.Poker;
import enums.TipoMano;
import exceptions.ExceptionValidationPoker;

class PokerTest {
	
	public static final String NAME_JUGADOR_BLACK = "Negro";
	public static final String NAME_JUGADOR_WHITE = "Blanco";
	
	private Poker juego = null; 
	
	@BeforeEach
	public void initPoker(){
		juego = new Poker();
	}
	
    @Nested
    @DisplayName("carta alta")
    class TestsCartaAlta {
    	
        @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C AH Negro gana. - con la carta alta: As ")
        @Test
        void testCartaAlta() throws ExceptionValidationPoker {
        	
        	// given    	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 9C KD"));
        	    	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.CARTA_ALTA);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }

        @DisplayName("Blanco: 2H 3D 5S 9C KD  Negro: 2C 3H 4S 8C 2C Negro gana. - con par: 2 ")
        @Test
        void testCartaAlta2() throws ExceptionValidationPoker {    	
        	
        	// given	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 9C KD"));
        	    	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C 2C"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.PAR);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
        
        @DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
        @Test
        void testCartaAlta3() {
        	
        	// given
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C"));
        	    	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S AC AH"));
        	
        	// when
            Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
            	juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
            });

            // then
            String expectedMessage = ExceptionValidationPoker.HAND_INVALID;
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    	
    }

    @Nested
    @DisplayName("par")
    class TestsPar {
    	
    	@DisplayName("Blanco: 2H 3D 5S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con par: Rey ")
    	@Test
    	void testPar() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S KC KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S AC AH Negro gana. - con par: As ")
    	@Test
    	void testPar2() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S AC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C  Negro: 2C 3H 4S AC AH Se lanza Exception de error en la mano")
    	@Test
    	void testPar3() {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S AC AH"));
    		
    		// when
    		Exception exception = assertThrows(ExceptionValidationPoker.class, () -> {
    			juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
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
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 4S JC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.CARTA_ALTA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    }

    @Nested
    @DisplayName("doble par")
    class TestsDoblePar {
    	
    	@DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con dos pares: Rey y Tres")
    	@Test
    	void testDosPares() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 3S KC KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.DOBLE_PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());	
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 3S AC AH Negro gana. - con dos pares: As y Tres ")
    	@Test
    	void testDosPares2() throws ExceptionValidationPoker {    	
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 3S AC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.DOBLE_PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 3H 3D 5S 5C KD  Negro: 2C 3H 3S AC AH Negro gana. - empate de dos pares: gana As y Tres ")
    	@Test
    	void testDosPares3() throws ExceptionValidationPoker {    	
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("3H 3D 5S 5C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 3S AC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.DOBLE_PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: KH KS QH QS 10H  Negro: KC KD QC QD 9H Blanco gana. - empate de dos doble par : gana 10 ")
    	@Test
    	void testDosParesEmpate() throws ExceptionValidationPoker {    	
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("KH KS QH QS 10H"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("KC KD QC QD 9H"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.CARTA_ALTA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H 4S 4C AH Blanco gana. - con dos pares: Rey y Tres")
        @Test
        void testDosParesVsPar() throws ExceptionValidationPoker {	
        	
        	// given
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 3S KC KD"));
        	    	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 4C AH"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.DOBLE_PAR);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());	
        }
    }
    
    @Nested
    @DisplayName("terna")
    class TestsTerna {
    	
    	@DisplayName("Blanco: 2H 3D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con Terna: Rey ")
    	@Test
    	void testTerna() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D KS KC KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.TERNA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H AS AC AH Negro gana. - con Terna: As ")
    	@Test
    	void testTerna2() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H AS AC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.TERNA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 3D 3S KC KD  Negro: 2C 3H AS AC AH Negro gana. - con Terna: As ")
        @Test
        void testTernaVsDoblePar() throws ExceptionValidationPoker {
        	
        	// given
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 3S KC KD"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H AS AC AH"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.TERNA);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
        
    	@DisplayName("Blanco: JH JD JS 8C KD  Negro: JC JH JS 2C 4H Blanco gana. - con Carta Alta: K ")
    	@Test
    	void testTernaEmpate() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("JH JD JS 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("JC JH JS 2C 4H"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.CARTA_ALTA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    }
    
    @Nested
    @DisplayName("escalera")
    class TestsEscalera {
    	
    	@DisplayName("Blanco: 2H 3D 4S 5C 6D  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera ")
    	@Test
    	void testEscalera() throws ExceptionValidationPoker {    	
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 4S 5C 6D"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.ESCALERA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3H 4S 5C 6H Negro gana. - con Escalera ")
    	@Test
    	void testEscalera2() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 5C 6H"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.ESCALERA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 3D 4S 5C 6D  Negro: 2H 3D KS KC KD Blanco gana. - con Escalera ")
        @Test
        void testEscaleraVsTerna() throws ExceptionValidationPoker {    	
        	
        	// given
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 4S 5C 6D"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2H 3D KS KC KD"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.ESCALERA);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
    }
    
    @Nested
    @DisplayName("color")
    class TestsColor {
    	
    	@DisplayName("Blanco: 2H 3H 5H KH 8H  Negro: 2C 3H 4S 8C AH Blanco gana. - con color ")
    	@Test
    	void testColor() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3H 5H KH 8H"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.COLOR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C AC QC Negro gana. - con color ")
    	@Test
    	void testColor2() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3C 4C AC QC"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.COLOR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 3D 4S 5C 6D  Negro: 2C 3C 4C AC QC Negro gana. - con color ")
        @Test
        void testColorVsEscalera() throws ExceptionValidationPoker {
        	
        	// given	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 4S 5C 6D"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3C 4C AC QC"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.COLOR);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
        
    	@DisplayName("Blanco: 2D 3D 5D 8D KD  Negro: 2C 3C 4C AC QC Negro gana. - con carta alta: As")
    	@Test
    	void testColorEmpate() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2D 3D 5D 8D KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3C 4C AC QC"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.CARTA_ALTA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    }

    @Nested
    @DisplayName("full house")
    class TestsFulHouse {
    	
    	@DisplayName("Blanco: 2H 2D KS KC KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con full house ")
    	@Test
    	void testFullHouse() throws ExceptionValidationPoker {
    		
    		// given
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D KS KC KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.FULL_HOUSE);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());	
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 2S AC AH Negro gana. - con full house ")
    	@Test
    	void testFullHouse2() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 2S AC AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.FULL_HOUSE);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 2D KS KC KD  Negro: 2H 3H 5H KH 8H Blanco gana. - con full house ")
        @Test
        void testFullHouseVsColor() throws ExceptionValidationPoker {
        	
        	// given
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D KS KC KD"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2H 3H 5H KH 8H"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.FULL_HOUSE);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());	
        }
        
    	@DisplayName("Blanco: 6H 6D KS KC KD  Negro: 8C 8H KS KC KH Negro gana. - con par: 8 ")
    	@Test
    	void testFullHouseEmpate() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("6H 6D KS KC KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("8C 8H KS KC KH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.PAR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    }
    
    @Nested
    @DisplayName("poker")
    class TestsPoker {
    	
    	@DisplayName("Blanco: 2H 2D 2S 2C KD  Negro: 2C 3H 4S 8C AH Blanco gana. - con poker: 2 ")
    	@Test
    	void testPoker() throws ExceptionValidationPoker {
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D 2S 2C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.POKER);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 2H 2S 2D KH Negro gana. - con poker: 2 ")
    	@Test
    	void testPoker2() throws ExceptionValidationPoker {  
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 2S 2D KH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.POKER);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2H 2D KS KC KD  Negro: 2C 2H 2S 2D KH Negro gana. - con poker: 2 ")
        @Test
        void testPokerVsFullHouse() throws ExceptionValidationPoker {  
        	
        	// given	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D KS KC KD"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 2S 2D KH"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.POKER);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
        
    	@DisplayName("Blanco: 2H 2D 2S 2C JD  Negro: 2C 2H 2S 2D KH Negro gana. - con carta alta: K ")
    	@Test
    	void testPokerEmpate() throws ExceptionValidationPoker {  
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 2D 2S 2C JD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 2S 2D KH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.CARTA_ALTA);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    }

    @Nested
    @DisplayName("escalera color")
    class TestsEscaleraColor {
    	
    	@DisplayName("Blanco: 2H 3H 4H 5H 6H  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Color: Corazon")
    	@Test
    	void testEscaleraColor() throws ExceptionValidationPoker {
    		
    		// given 	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3H 4H 5H 6H"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then   
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.ESCALERA_COLOR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());	
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 2C 3C 4C 5C 6C Negro gana. - con Escalera Color: Trebol")
    	@Test
    	void testEscaleraColor2() throws ExceptionValidationPoker {    
    		
    		// given  	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3C 4C 5C 6C"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.ESCALERA_COLOR);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner()); 	
    	}
    	
        @DisplayName("Blanco: 2C 3C 4C 5C 6C Negro: 2C 2H 2S 2D KH Blanco gana. - con Escalera de Color: Trebol")
        @Test
        void testEscalerColorVsPoker() throws ExceptionValidationPoker {    	
        	
        	// given	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2C 3C 4C 5C 6C"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 2H 2S 2D KH"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.ESCALERA_COLOR);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
    }
    
    @Nested
    @DisplayName("escalera real")
    class TestsEscaleraReal {
    	
    	@DisplayName("Blanco: 10H JH QH KH AH  Negro: 2C 3H 4S 8C AH Blanco gana. - con Escalera Real: Corazon ")
    	@Test
    	void testEscaleraReal() throws ExceptionValidationPoker {   
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("10H JH QH KH AH"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("2C 3H 4S 8C AH"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_WHITE, TipoMano.ESCALERA_REAL);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
    	@DisplayName("Blanco: 2H 3D 5S 8C KD  Negro: 10C JC QC KC AC Negro gana. - con Escalera Real: Trebol")
    	@Test
    	void testEscaleraReal2() throws ExceptionValidationPoker {    	
    		
    		// given	
    		final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2H 3D 5S 8C KD"));
    		
    		final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("10C JC QC KC AC"));
    		
    		// when
    		final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
    		
    		// then 
    		final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.ESCALERA_REAL);
    		assertEquals(messageExpected, actualCardWinner.getMessageWinner());
    	}
    	
        @DisplayName("Blanco: 2C 3C 4C 5C 6C  Negro: 10C JC QC KC AC Negro gana. - con Escalera Real: Trebol")
        @Test
        void testEscaleraRealVsEscalerColor() throws ExceptionValidationPoker {    	
        	
        	// given	
        	final Jugador handPlayerWhite = new Jugador(NAME_JUGADOR_WHITE, new Mano("2C 3C 4C 5C 6C"));
        	
        	final Jugador handPlayerBlack = new Jugador(NAME_JUGADOR_BLACK, new Mano("10C JC QC KC AC"));

        	// when
        	final Ganador actualCardWinner = juego.getValidationByBestHand(handPlayerWhite, handPlayerBlack);
        	
        	// then 
        	final String messageExpected = buildMessageExpected(NAME_JUGADOR_BLACK, TipoMano.ESCALERA_REAL);
        	assertEquals(messageExpected, actualCardWinner.getMessageWinner());
        }
    }

    private String buildMessageExpected (String namePlayer, TipoMano manoGanadora) {
    	return "El ganador es el jugador: "+namePlayer+" Con la mano de tipo: "+manoGanadora;
    }
    
}