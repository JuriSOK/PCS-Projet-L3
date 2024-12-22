package up.mi.paa.projet.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMenuAutomatique {

    private File tempFile;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() throws IOException {
        // Créer un fichier temporaire pour le test
        tempFile = File.createTempFile("testColonie", ".txt");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        // Supprimer le fichier temporaire après le test
        if (tempFile.exists()) {
            tempFile.delete();
        }
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testConstructeur() {
        MenuAutomatique menu = new MenuAutomatique(tempFile.getAbsolutePath());
        assertNotNull(menu, "L'objet MenuAutomatique ne doit pas être nul");
    }

    @Test
    public void testDemarrage() throws IOException {
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

        String input = "1\n" + // Lancer la résolution automatique
                       "2\n" + // Sauvegarder la solution actuelle
                       "oui\n" + // Utiliser un fichier existant
                       tempFile.getAbsolutePath() + "\n" + // Chemin du fichier existant
                       "3\n"; // Quitter le programme

        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        MenuAutomatique menu = new MenuAutomatique(tempFile.getAbsolutePath());
        menu.demarrage();

        String expectedOutput = "----- Veuillez choisir votre action -----\n" +
                                "1) Lancer la résolution automatique\n" +
                                "2) Sauvegarder la solution actuelle\n" +
                                "3) Quitter le programme\n" +
                                "Voici l'affection automatique proposée :\n" +
                                "Alice : Ressource1\n" +
                                "Bob : Ressource2\n" +
                                "Nombre de colons jaloux dans cette configuration : 0\n" +
                                "----- Veuillez choisir votre action -----\n" +
                                "1) Lancer la résolution automatique\n" +
                                "2) Sauvegarder la solution actuelle\n" +
                                "3) Quitter le programme\n" +
                                "Voulez-vous utiliser un fichier déjà existant ? (oui/non) : " +
                                "Entrez le chemin du fichier à utiliser (chemin absolu) : " +
                                "Fichier existant chargé : " + tempFile.getAbsolutePath() + "\n" +
                                "Solution enregistré ! :)\n"+
                                "----- Veuillez choisir votre action -----\n" +
                                "1) Lancer la résolution automatique\n" +
                                "2) Sauvegarder la solution actuelle\n" +
                                "3) Quitter le programme\n" +
                                "Fin du logiciel\n";

        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}

