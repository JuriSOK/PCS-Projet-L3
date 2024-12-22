package up.mi.paa.projet.colonie;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;



import org.junit.jupiter.api.BeforeEach;

import java.util.List; 
import java.util.ArrayList; 
class TestColonieSpatiale {

	private static Colon colonA;
	private static Colon colonB;
	private static Colon colonC;
	private static Ressources ressource1;
	private static Ressources ressource2;
	private static Ressources ressource3;
	private static List<Colon> listeColons ; 
	private static List<Ressources> listeRessources; 
	private static ColonieSpatiale colonieTest; 
	
	@BeforeEach
	void BeforeEach() {
		
		colonA = new Colon("A");
        colonB = new Colon("B");
        colonC = new Colon("C");
        
        ressource1 = new Ressources("1");
        ressource2 = new Ressources("2");
        ressource3 = new Ressources("3");
		
	    
        colonA.ajouterPref(ressource1);
        colonA.ajouterPref(ressource2);
        
        colonB.ajouterPref(ressource1);
        colonB.ajouterPref(ressource2);

        colonA.neSaimePas(colonB);
	    
        listeColons = new ArrayList<>();
        listeColons.add(colonA);
        listeColons.add(colonB);
        
        listeRessources = new ArrayList<>();
        listeRessources.add(ressource1);
        listeRessources.add(ressource2);
        
        colonieTest = new ColonieSpatiale(listeColons, listeRessources);
	   
	    
	}
	
	@Test
	void testConstructeur() {
		/*
		 * On commence par vérifir la création de l'objet colonie.
		 * Ensuite on vérifie la création de chaque ensemble et map de la colonie.
		 * 
		 */
	
		assertNotNull(colonieTest);
		
		assertNotNull(colonieTest.getColonie());
		assertEquals(2, colonieTest.getColonie().size());
		
		assertNotNull(colonieTest.getEnsembleRessources());
		assertEquals(2, colonieTest.getEnsembleRessources().size());
		
		assertNotNull(colonieTest.getEnsembleRessourcesAffectees());
		assertEquals(0, colonieTest.getEnsembleRessourcesAffectees().size());
		
	}
	
	@Test
	void testChercheColon() {
		assertTrue(colonieTest.chercheColon("A"));
	}
	
	@Test
	void testChercheColonInexistant() {
		assertFalse(colonieTest.chercheColon("C"));
	}
	

	
	@Test
	void testGetColonParNom() {
		assertEquals(colonA, colonieTest.getColonParNom("A"));
	}
	
	@Test
	void testGetColonParNomInexistant() {
		assertNull(colonieTest.getColonParNom("C"));
	}
	
	
	
	@Test
	void testgetRessourceParNom() {
		assertEquals(ressource1, colonieTest.getRessourceParNom("1"));
	}
	
	@Test
	void testgetRessourceParNomInexistante() {
		assertNull(colonieTest.getRessourceParNom("3"));
	}
	
	
	
	@Test
	void testAffectionRessourcesNaive() {
		
		 //On effectue l'affectation des ressources
        colonieTest.affectionRessourcesNaive();
        
        //Le colon A doit obtenir la ressource 1 car il est premier et il la préfère.
        assertEquals(ressource1, colonA.getRessourceAffecte());
        
        // Colon B doit obtenir la ressource 2 car la ressource 1 est déjà prise
        assertEquals(ressource2, colonB.getRessourceAffecte());
		
	}
	@Test
	void testAffectationRessourcesOptimise() {
		
        // Ajout du colon C et de la ressource 3
        ressource3 = new Ressources("3");
        colonC = new Colon("C");
        listeRessources.add(ressource3);
        listeColons.add(colonC);
        colonieTest = new ColonieSpatiale(listeColons, listeRessources);

  
        colonA.neSaimePas(colonC);
        colonA.laverListePref();
        colonB.laverListePref();

        colonA.ajouterPref(ressource2);
        colonA.ajouterPref(ressource3);
        colonA.ajouterPref(ressource1);

        colonB.ajouterPref(ressource2);
        colonB.ajouterPref(ressource1);
        colonB.ajouterPref(ressource3);

        colonC.ajouterPref(ressource2);
        colonC.ajouterPref(ressource1);
        colonC.ajouterPref(ressource3);

        // Ici, les jaloux sont B et C car A a eu la ressource 2 et les deux souhaitent l'avoir.
        colonieTest.affectionRessourcesNaive();
        assertEquals(2, colonieTest.calculerJaloux());
     
        
        /*
         * Ici, seul A est jaloux car il a eu la ressource 3, alors que C a eu la deuxième, mais ça permet à B de ne plus être jaloux de A
         * car B a eu la 1 et pour lui la ressource 3 est la moins bonne pour lui.
         * C n'est plus jaloux car il a eu la meilleur ressource pour lui.
         */
        colonieTest.affectionRessourcesOptimise(5);
        assertTrue(colonieTest.getEnsembleRessourcesAffectees().contains(colonA.getRessourceAffecte()));
        assertTrue(colonieTest.getEnsembleRessourcesAffectees().contains(colonB.getRessourceAffecte()));
        assertTrue(colonieTest.getEnsembleRessourcesAffectees().contains(colonC.getRessourceAffecte()));
      

        assertEquals(1, colonieTest.calculerJaloux());
		
	}
	@Test
	void testSwapRessources() {
		
        colonieTest.affectionRessourcesNaive();

        assertEquals(ressource1, colonA.getRessourceAffecte());
        assertEquals(ressource2, colonB.getRessourceAffecte());

        colonieTest.swapRessources(colonA, colonB);
        
        assertEquals(ressource2, colonA.getRessourceAffecte());
        assertEquals(ressource1, colonB.getRessourceAffecte());
  
	}
	
		
	
	@Test
	void testCalculerJaloux() {
        colonieTest.affectionRessourcesNaive();

        // Le Colon A a obtenu la ressource 1 donc colon B est jaloux car il l'a voulait aussi.
        assertEquals(1, colonieTest.calculerJaloux());
	}
	
}
