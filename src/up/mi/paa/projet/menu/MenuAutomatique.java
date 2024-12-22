package up.mi.paa.projet.menu;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.util.ColonieFactory;
import up.mi.paa.projet.util.UtilitaireFichierColonie;

/**
 * Classe gérant un menu automatique permettant de configurer et manipuler une colonie spatiale
 * à partir d'un fichier de configuration.
 * 
 * Ce menu propose les options suivantes :
 * - Lancer une résolution automatique des affectations de ressources.
 * - Sauvegarder la solution actuelle.
 * - Quitter le programme.
 */
public class MenuAutomatique extends Menu {

	
	/**
	 * Le chemin du fichier contenant la colonie sous forme de fichier.
	 */
    private String cheminFichier;

    /**
     * Constructeur de la classe MenuAutomatique.
     * Initialise le scanner via la classe parente et reçoit le chemin du fichier de configuration.
     *
     * @param cheminFichier chemin vers le fichier contenant la configuration initiale de la colonie.
     */
    public MenuAutomatique(String cheminFichier) {
        super();
        this.cheminFichier = cheminFichier;
    }

    /**
     * Démarre le menu automatique.
     * Charge une colonie à partir du fichier fourni, puis redirige vers le menu principal.
     */
    public void demarrage() {
    	
        ColonieSpatiale colonie =  ColonieFactory.creeColonieSpatialeParFichier(cheminFichier);
        if (colonie == null) {
            System.out.println("Le programme se termine car la colonie n'a pas pu être chargée.");
            return;
        }

        int reponse = 0;
        while (reponse != 3) {
            reponse = menuPrincipal(colonie);
            switch (reponse) {
                case 1:
                    colonie = menuResolutionAuto(colonie);
                    break;

                case 2:
                    menuSauvegarde(colonie);
                    break;

                case 3:
                    menuFin();
                    break;
            }
        }
    }

    /**
     * Menu principal offrant trois options :
     * - La résolution automatique pour affecter les ressources
     * - La sauvegarde de la solution actuelle dans un fichier au choix.
     * - La fin du programme
     *
     * @param colonie la colonie à manipuler.
     * @return l'option choisie par l'utilisateur.
     */
    private int menuPrincipal(ColonieSpatiale colonie) {
        int reponse = -1;
        boolean saisieValide = false;

        while (!saisieValide) {
            try {
                System.out.println("----- Veuillez choisir votre action -----");
                System.out.println("1) Lancer la résolution automatique");
                System.out.println("2) Sauvegarder la solution actuelle");
                System.out.println("3) Quitter le programme");
                reponse = scanner.nextInt();
                scanner.nextLine(); // Consomme le tampon pour éviter les erreurs.

                if (reponse < 1 || reponse > 3) {
                    throw new IllegalArgumentException("Choisissez un nombre entre 1 et 3.");
                }
                
                saisieValide = true;
                
                
            } catch (InputMismatchException e) {
            	System.out.println("Erreur : Veuillez entrer un nombre entier.");
                scanner.nextLine(); // Vide le tampon
                
            } catch (IllegalArgumentException e) {
            	System.out.println(e.getMessage());
                scanner.nextLine(); // Vide le tampon
            }
        }
        return reponse;
    }

    /**
     * La méthode permet d'affecter de manière automatique les ressources.
     *
     * @param colonie la colonie courante.
     * @return la colonie mise à jour après la résolution.
     */
    private ColonieSpatiale menuResolutionAuto(ColonieSpatiale colonie) {
        colonie.affectionRessourcesOptimise(5);
        System.out.println("Voici l'affection automatique proposée :");
        colonie.afficherAffectation();
        System.out.println("Nombre de colons jaloux dans cette configuration : " + colonie.calculerJaloux());
        return colonie;
    }
    
    /**
     * Permet de sauvegarder l'état actuel de la colonie dans un fichier existant, ou un fichier qui sera crée dans le répertoire
     * solutions si l'utilisateur n'a pas de fichier disponible.
     *
     * @param colonie la colonie à sauvegarder.
     */
    
    private void menuSauvegarde(ColonieSpatiale colonie) {
    	
    	String cheminFichier = "";
    	
  
    	String reponse;
    	
    	while (true) {
    		
    		System.out.print("Voulez-vous utiliser un fichier déjà existant ? (oui/non) : ");
    		reponse = scanner.nextLine().trim().toLowerCase();
    		if (reponse.equals("oui") || reponse.equals("non")) {
    			break;
    		} else {
    			System.out.println("Réponse invalide. Veuillez répondre par 'oui' ou 'non'.");
    		}
    		
    	}
    	
    	try {
    		
    		if (reponse.equals("oui")) {

    			System.out.print("Entrez le chemin du fichier à utiliser (chemin absolu) : ");
    			cheminFichier = scanner.nextLine().trim();
    			
    			File fichier = new File(cheminFichier);
    			if (!fichier.exists()) {
                    throw new IOException("Le fichier spécifié n'existe pas.");
                }
    			
    			System.out.println("Fichier existant chargé : " + cheminFichier);
    		}
    		else {
    			
    			System.out.print("Entrez le nom du fichier pour sauvegarder la solution (sans extension) : \n"
    					+ "Le fichier sera enregistré dans le dossier 'solutions' prévu pour cela ");
    			String nomFichier = scanner.nextLine().trim();
    			
    			String repertoireSolution = "../PROJET_PAA_FINAL/solutions";
    			cheminFichier = repertoireSolution + File.separator + nomFichier + ".txt";
                System.out.println("Fichier de sauvegarde créé : " + cheminFichier);
                
    			
    		}
    		UtilitaireFichierColonie.sauvegarderSolutionFichier(colonie, cheminFichier);
    	}
    	catch (IOException e) {
    		System.out.println("Erreur de fichier : " + e.getMessage());
    	}
    	
    }
  
    
    /**
     * Termine le programme et ferme les ressources utilisées.
     */
    private void menuFin() {
        System.out.println("Fin du logiciel");
        scanner.close();
    }
}
