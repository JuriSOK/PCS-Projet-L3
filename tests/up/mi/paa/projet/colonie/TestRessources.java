package up.mi.paa.projet.colonie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach; 

class TestRessources {

	private static Ressources testRessource1;
	private static Ressources testRessource2;
	private static Ressources testRessource3;
	
	@BeforeEach
	void BeforeEach() {
		testRessource1 = new Ressources("1");
		testRessource2 = new Ressources("1");
		testRessource3 = new Ressources("2");
	}
	
	@Test
	void testConstructeur() {
		assertTrue(testRessource1 != null); 
		assertEquals("1",testRessource1.getNom()); 
	}
	
	@Test
	void testEqals() {
		/**
		 * On vérifie qu'un objet est bien égale à lui-même
		 */
		assertTrue(testRessource1.equals(testRessource1));
		/**
		 * On vérifie qu'un objet n'est pas égale à null
		 */
		assertFalse(testRessource1.equals(null));
		/**
		 * On vérifie que deux objets ayant des attributs de même valeurs, ici leurs noms, sont dits égaux
		 */
		assertTrue(testRessource1.equals(testRessource2));
		/**
		 * On vérifie que deux objets n'ayant pas leurs attributs de même valeurs, 
		 * ici leurs noms, ne sont donc pas égaux
		 */
		assertFalse(testRessource1.equals(testRessource3));
		
	}

}
