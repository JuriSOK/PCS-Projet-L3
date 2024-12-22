package up.mi.paa.projet.menu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMenuManuel {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    
    @Test
    public void testConstructeur() {
    	 MenuManuel menu = new MenuManuel();
        assertNotNull(menu, "L'objet MenuManuel ne doit pas être nul");
    }

    @Test
    public void testDemarrage() {
        String input = "3\n" + // Nombre de colons
                       "Alice\n" + // Nom du premier colon
                       "Bob\n" + // Nom du deuxième colon
                       "Charlie\n" + // Nom du troisième colon
                       "Ressource1\n" + // Nom de la première ressource
                       "Ressource2\n" + // Nom de la deuxième ressource
                       "Ressource3\n" + // Nom de la troisième ressource
                       "2\n" + // Ajouter les préférences d'un colon
                       "Alice Ressource1 Ressource2 Ressource3\n" + // Préférences pour Alice
                       "2\n" + // Ajouter les préférences d'un colon
                       "Bob Ressource1 Ressource2 Ressource3\n" + // Préférences pour Bob
                       "2\n" + // Ajouter les préférences d'un colon
                       "Charlie Ressource1 Ressource2 Ressource3\n" + // Préférences pour Charlie
                       "3\n" + // Quitter le premier menu
                       "1\n" + // Échanger les ressources de deux colons
                       "Alice\n" + // Nom du premier colon
                       "Bob\n" + // Nom du deuxième colon
                       "2\n" + // Afficher le nombre de colons jaloux
                       "3\n"; // Quitter le second menu

        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        MenuManuel menu = new MenuManuel();
        menu.demarrage();

        String expectedOutput = "----- Veuillez définir un nombre de colons -----\n" +
                                "----- Saisie des noms des colons -----\n" +
                                "Entrez le nom du colon #1 :\n" +
                                "Entrez le nom du colon #2 :\n" +
                                "Entrez le nom du colon #3 :\n" +
                                "----- Saisie des noms des ressources -----\n" +
                                "Entrez le nom de la ressource #1 :\n" +
                                "Entrez le nom de la ressource #2 :\n" +
                                "Entrez le nom de la ressource #3 :\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 pour ajouter une relation entre deux colons\n" +
                                "Entrer 2 pour ajouter les préférences d'un colon\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Pour rentrer la liste des préférences pour un colon, veuillez suivre le paterne suivant : \n" +
                                "NOM_COLON RESSOURCE1 RESSOURCE 2 ... RESSOURCE N\n" +
                                "Ajout réussi pour le colon Alice\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 pour ajouter une relation entre deux colons\n" +
                                "Entrer 2 pour ajouter les préférences d'un colon\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Pour rentrer la liste des préférences pour un colon, veuillez suivre le paterne suivant : \n" +
                                "NOM_COLON RESSOURCE1 RESSOURCE 2 ... RESSOURCE N\n" +
                                "Ajout réussi pour le colon Bob\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 pour ajouter une relation entre deux colons\n" +
                                "Entrer 2 pour ajouter les préférences d'un colon\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Pour rentrer la liste des préférences pour un colon, veuillez suivre le paterne suivant : \n" +
                                "NOM_COLON RESSOURCE1 RESSOURCE 2 ... RESSOURCE N\n" +
                                "Ajout réussi pour le colon Charlie\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 pour ajouter une relation entre deux colons\n" +
                                "Entrer 2 pour ajouter les préférences d'un colon\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Vérification des erreurs : \n" +
                                "Aucune erreur détecté \n" +
                                "La colonie : \n" +
                                "Nom colon : Alice\n" +
                                "Il n'aime pas : \n" +
                                "Voici sa liste de préférences, du plus aimé au moins aimé : \n" +
                                "Ressource1 Ressource2 Ressource3 \n" +
                                "Nom colon : Bob\n" +
                                "Il n'aime pas : \n" +
                                "Voici sa liste de préférences, du plus aimé au moins aimé : \n" +
                                "Ressource1 Ressource2 Ressource3 \n" +
                                "Nom colon : Charlie\n" +
                                "Il n'aime pas : \n" +
                                "Voici sa liste de préférences, du plus aimé au moins aimé : \n" +
                                "Ressource1 Ressource2 Ressource3 \n" +
                                "\n"+
                                "Voici l'affection temporaire des ressources pour les colons : \n" +
                                "Alice : Ressource1\n" +
                                "Bob : Ressource2\n" +
                                "Charlie : Ressource3\n" +
                                "-----Bienvenue dans le second menu-----\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 échanger les ressources de deux colons\n" +
                                "Entrer 2 pour afficher le nombre de colons jaloux\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Entrez le nom du premier colon\n" +
                                "Entrez le nom du deuxième colon\n" +
                                "Voici la nouvelle affectation après échange : \n" +
                                "Alice : Ressource2\n" +
                                "Bob : Ressource1\n" +
                                "Charlie : Ressource3\n" +
								"-----Bienvenue dans le second menu-----\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 échanger les ressources de deux colons\n" +
                                "Entrer 2 pour afficher le nombre de colons jaloux\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Le nombre de colons jaloux est : 0\n" +
                                "-----Bienvenue dans le second menu-----\n" +
                                "-----Veuillez choisir votre action-----\n" +
                                "Entrer 1 échanger les ressources de deux colons\n" +
                                "Entrer 2 pour afficher le nombre de colons jaloux\n" +
                                "Entrer 3 pour quitter ce menu\n" +
                                "Fin du logiciel\n";

        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}

