package up.mi.paa.projet.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.colonie.Colon;
import up.mi.paa.projet.colonie.Ressources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtilitaireFichierColonie {

    private File tempFile;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void BeforeEach() throws IOException {
        // Créer un fichier temporaire pour le test
        tempFile = File.createTempFile("testColonie", ".txt");
        System.setOut(new PrintStream(outContent));
       
        
        
    }

    @AfterEach
    public void AfterEach() {
        // Supprimer le fichier temporaire après le test
        if (tempFile.exists()) {
            tempFile.delete();
        }
        System.setOut(originalOut);
    }

    @Test
    public void testLireLignesFichier() throws IOException {
        // Écrire des lignes dans le fichier temporaire
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("colon(Alice).\n");
            writer.write("colon(Bob).\n");
            writer.write("ressource(Ressource1).\n");
        }

        List<String> lignes = UtilitaireFichierColonie.lireLignesFichier(tempFile.getAbsolutePath());
        assertNotNull(lignes);
        assertEquals(3, lignes.size());
        assertEquals("colon(Alice).", lignes.get(0));
        assertEquals("colon(Bob).", lignes.get(1));
        assertEquals("ressource(Ressource1).", lignes.get(2));
    }
    
    @Test
    public void testLireLignesFichierMauvais() throws IOException {
        // Écrire des lignes dans le fichier temporaire
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("colon(Alice).\n");
            writer.write("colon(Bob).\n");
            writer.write("ressource(Ressource1).\n");
        }

        List<String> lignes = UtilitaireFichierColonie.lireLignesFichier(tempFile.getAbsolutePath());
        assertNotNull(lignes);
        assertEquals(3, lignes.size());
        assertNotEquals("colon(Alice)", lignes.get(0));
        assertNotEquals("colon(Bob)", lignes.get(1));
        assertNotEquals("ressource(Ressource1)", lignes.get(2));
    }
    
    

    @Test
    public void testVerifierFichierValide() throws IOException {
        // Écrire un fichier de configuration valide
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("colon(Alice).\n");
            writer.write("colon(Bob).\n");
            writer.write("ressource(Ressource1).\n");
            writer.write("ressource(Ressource2).\n");
            writer.write("deteste(Alice,Bob).\n");
            writer.write("preferences(Alice,Ressource1,Ressource2).\n");
            writer.write("preferences(Bob,Ressource2,Ressource1).\n");
        }

        boolean isValid = UtilitaireFichierColonie.verifierFichier(tempFile.getAbsolutePath());
        assertTrue(isValid);
    }

    @Test
    public void testVerifierFichierInvalide() throws IOException {
        // Écrire un fichier de configuration invalide
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("colon(Alice)\n");
            writer.write("ressource(Ressource1)\n");
            writer.write("deteste(Alice,Bob)\n"); // Bob n'est pas défini
        }

        boolean isValid = UtilitaireFichierColonie.verifierFichier(tempFile.getAbsolutePath());
        assertFalse(isValid);
    }

    @Test
    public void testSauvegarderSolutionFichier() throws IOException {
    	
    	List<Colon> colons = new ArrayList<>();
    	colons.add(new Colon("Alice"));
        colons.add(new Colon("Bob"));
        
        
        List<Ressources> ressources = new ArrayList<>();
        ressources.add(new Ressources("Ressource1"));
        ressources.add(new Ressources("Ressource2"));
        
        ColonieSpatiale colonie = new ColonieSpatiale(colons, ressources);
        colonie.getColonParNom("Alice").setRessourceAffecte(colonie.getRessourceParNom("Ressource1"));
        colonie.getColonParNom("Bob").setRessourceAffecte(colonie.getRessourceParNom("Ressource2"));
        
        // Sauvegarder la solution dans un fichier temporaire
        UtilitaireFichierColonie.sauvegarderSolutionFichier(colonie, tempFile.getAbsolutePath());
        
        // Vérifier le contenu du fichier
        List<String> lignes = UtilitaireFichierColonie.lireLignesFichier(tempFile.getAbsolutePath());
        assertNotNull(lignes);
        assertEquals(2, lignes.size());
        assertEquals("Alice:Ressource1", lignes.get(0).trim());
        assertEquals("Bob:Ressource2", lignes.get(1).trim()); 
    	
    }
        
}

