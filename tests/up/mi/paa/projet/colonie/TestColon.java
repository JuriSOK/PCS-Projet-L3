package up.mi.paa.projet.colonie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;


/**
 * Classe de test afin de tester la classe Colon du package up.mi.paa.projet.colonie
 */
class TestColon {
    
	private static Colon colonA;
	private static Colon colonB;
	private static Ressources ressource1;
    private static Ressources ressource2;
	
	@BeforeEach
	void BeforeEach() {
		colonA = new Colon("A");
        colonB = new Colon("B");
        ressource1 = new Ressources("Eau");
        ressource2 = new Ressources("Oxygène");
	}
	
	
	
	@Test
	void testConstructeur() {
		/*
		 * On vérifie que le colon est bien créé, puis que le nom est bien celui choisi lors de sa création.
		 * Puis on vérifie que son ensemble de colons non aimés est créée (initialiser) et qu'elle est bien vide.
		 * Nous faisons la même chose pour sa Map qui représente ses ressources préférés, puis également pour sa ressource affectée.
		 */
		assertNotNull(colonA); 
		assertEquals("A", colonA.getNom());
		assertNotNull(colonA.getAimePas());
		assertTrue(colonA.getAimePas().isEmpty());
		assertNotNull(colonA.getRessourcesPreferes());
		assertTrue(colonA.getRessourcesPreferes().isEmpty());
		assertNull(colonA.getRessourceAffecte());
		
	}
	@Test
	void testNeSaimePas() {
		
		colonA.neSaimePas(colonB);
		// On Vérifie que les deux colons se détestent bien réciproquement.
		assertTrue(colonA.getAimePas().contains(colonB));
		assertTrue(colonB.getAimePas().contains(colonA));
	}
	
	@Test
    void testNeSaimePasSoiMemeInterdite() {
		
        // Vérifie qu'un colon ne peut pas se détester lui-même.
        assertFalse(colonA.neSaimePas(colonA));
    }

	
	@Test
	void testajouterPref() {
		
		// Ajout de la ressource "Eau" pour le colonA, cela doit marcher car il n'a aucune préférence encore.
		assertTrue(colonA.ajouterPref(ressource1));
		assertTrue(colonA.getRessourcesPreferes().containsKey(ressource1));
		//On vérifie que la première ressource ajouté est bien sa préféré (donc indice 0).
		assertEquals(0, colonA.getRessourcesPreferes().get(ressource1));
	}
	
	@Test
    void testAjouterRessourceDejaExistanteDansPreferences() {
		// Ajout de la ressource "Eau" pour le colonA, cela doit marcher car il n'a aucune préférence encore.
        colonA.ajouterPref(ressource1);
    	// Ajout de la ressource "Eau" pour le colonA une deuxième fois, cela ne doit pas marcher.
        assertFalse(colonA.ajouterPref(ressource1)); 
	
	}
	

	
	@Test
	void testVerifAimePas() {

		Colon colonC = new Colon("C"); 
		colonA.neSaimePas(colonB);
		

		// On vérifie que A n'aime bien pas B.
		assertTrue(colonA.verifAimePas(colonB));
		// On vérifie que A ne déteste pas C et inversement.
		assertFalse(colonA.verifAimePas(colonC));
		
	}
	
	
	@Test
	void testLaverListePref() {
		
		colonA.ajouterPref(ressource1);
		assertEquals(1, colonA.getRessourcesPreferes().size());
		
		colonA.laverListePref();
		assertTrue(colonA.getRessourcesPreferes().isEmpty());
		
	}
	
	
	@Test
	void TestgetIndiceByRessources() {
		
		colonA.ajouterPref(ressource1);
		// On vérifie que l'indice de la ressource est correct (vue que c'est la première, c'est forcément sa préféré donc 0).
		assertEquals(0, colonA.getIndiceByRessources(ressource1));
		assertEquals(-1, colonA.getIndiceByRessources(ressource2));
		
	}
	@Test
	void testtoString() {
		colonA.ajouterPref(ressource1);
		colonA.ajouterPref(ressource2);
		/*
		 * On vérifie pour un colon avec seulement deux ressources prefs si sa méthode 
		 * toString() donne les infos correctes qui sont déjà rentrées dans la chaîne de caractères
		 */
		
		assertEquals("Nom colon : A\nIl n'aime pas : \nVoici sa liste de préférences, du plus aimé au moins aimé : \nEau Oxygène \n", colonA.toString()); 
	}

}
