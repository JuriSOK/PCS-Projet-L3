package up.mi.paa.projet.util;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.colonie.Colon;
import up.mi.paa.projet.colonie.Ressources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestColonieFactory {

    private File tempFile;

    @BeforeEach
    public void BeforeEach() throws IOException {
        // Créer un fichier temporaire pour le test
        tempFile = File.createTempFile("testColonie", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("colon(Alice).\n");
            writer.write("colon(Bob).\n");
            writer.write("colon(Charlie).\n");
            writer.write("ressource(Ressource1).\n");
            writer.write("ressource(Ressource2).\n");
            writer.write("ressource(Ressource3).\n");
            writer.write("deteste(Alice,Bob).\n");
            writer.write("deteste(Bob,Charlie).\n");
            writer.write("preferences(Alice,Ressource1,Ressource2,Ressource3).\n");
            writer.write("preferences(Bob,Ressource2,Ressource1,Ressource3).\n");
            writer.write("preferences(Charlie,Ressource3,Ressource1,Ressource2).\n");
        }
    }

    @AfterEach
    public void AfterEach() {
        // Supprimer le fichier temporaire après le test
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testCreeColonieSpatialeParFichier() {
        ColonieSpatiale colonie = ColonieFactory.creeColonieSpatialeParFichier(tempFile.getAbsolutePath());
        assertNotNull(colonie);

        Map<String, Colon> colons = colonie.getColonie();
        assertEquals(3, colons.size());

        Colon alice = colons.get("Alice");
        Colon bob = colons.get("Bob");
        Colon charlie = colons.get("Charlie");

        assertNotNull(alice);
        assertNotNull(bob);
        assertNotNull(charlie);

        assertTrue(alice.verifAimePas(bob));
        assertTrue(bob.verifAimePas(charlie));

        Map<Ressources, Integer> ressourcesAlice = alice.getRessourcesPreferes();
        assertEquals(3, ressourcesAlice.size());
        assertEquals(0, ressourcesAlice.get(colonie.getRessourceParNom("Ressource1")));
        assertEquals(1, ressourcesAlice.get(colonie.getRessourceParNom("Ressource2")));
        assertEquals(2, ressourcesAlice.get(colonie.getRessourceParNom("Ressource3")));

        Map<Ressources, Integer> ressourcesBob = bob.getRessourcesPreferes();
        assertEquals(3, ressourcesBob.size());
        assertEquals(0, ressourcesBob.get(colonie.getRessourceParNom("Ressource2")));
        assertEquals(1, ressourcesBob.get(colonie.getRessourceParNom("Ressource1")));
        assertEquals(2, ressourcesBob.get(colonie.getRessourceParNom("Ressource3")));

        Map<Ressources, Integer> ressourcesCharlie = charlie.getRessourcesPreferes();
        assertEquals(3, ressourcesCharlie.size());
        assertEquals(0, ressourcesCharlie.get(colonie.getRessourceParNom("Ressource3")));
        assertEquals(1, ressourcesCharlie.get(colonie.getRessourceParNom("Ressource1")));
        assertEquals(2, ressourcesCharlie.get(colonie.getRessourceParNom("Ressource2")));
    }
}

