package up.mi.paa.projet.menu;

import java.util.*;
import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.colonie.Ressources;
import up.mi.paa.projet.colonie.Colon;


/**
 * Classe permettant de gérer les interactions avec l'utilisateur
 * pour configurer et manipuler une colonie spatiale.
 * 
 * Ce menu propose deux phases principales :
 * - Saisie des colons et ressources, ainsi que des relations entre eux.
 * - Gestion des échanges de ressources et des colons jaloux dans la colonie.
 */
public class MenuManuel extends Menu {
	
	
	 /**
     * Constructeur de la classe MenuManuel.
     * Initialise le scanner via la classe parente.
     */
	public MenuManuel() {
		super();
				
	}
	
	
	/**
     * Démarre le menu principal.
     * Permet de définir le nombre de colons et ensuite redirige vers le menu pour rentrer leurs noms,
     * ainsi que les noms des ressources.
     * 
     * Après cela, suivant le choix de l'utilisateur, il permet d'effectuer l'action souhaitée.
     */
	public void demarrage() {
		
		int nbColons = setNbColons(); 
		ColonieSpatiale Colonie = setNomColonRessource(nbColons);
		int reponse = 0; 
        while(reponse != 3) {
        	reponse = menuAjout(Colonie);
        	switch (reponse) {
    		
    		    case 1:
    			    Colonie = menuAjoutRelations(Colonie);
    			    break;
    			
    		    case 2: 
    		    	Colonie = menuAjoutPreferences(Colonie);
    		    	break;
    			
    		    case 3: 
    		        reponse = menuFin1er(Colonie);
    		    	break;
    			
    		}
        }
        reponse = 0; 
        while(reponse != 3) {
        	reponse = secondMenu(Colonie);
        	switch (reponse) {
    		
    		case 1:
    			Colonie = menuEchangeRessources(Colonie);
    			break;
    			
    		case 2: 
    			menuAfficherJaloux(Colonie);
    			break;
    			
    		case 3: 
    			menuFin();
    			break;
    			
    		}
        }
	}
	/**
	 * 
	 * Menu intermédiaire permettant de définir le nombre de colons de la colonie (qui sera aussi le nombre de ressources).
	 * @return le nombre de colons.
	 */
	private int setNbColons() {
		boolean saisieValide = false;
		int nbColons = -1;
		
		while (!saisieValide) {
			
			try {
				
				System.out.println("----- Veuillez définir un nombre de colons -----");
				 nbColons = scanner.nextInt();
				 scanner.nextLine(); // On doit consommer le tampon, sinon on reste bloqué.
				 
				 saisieValide = true;
				 
			}
			catch (InputMismatchException e) { 
				System.out.println("Erreur : Veuillez entrer un nombre valide. (Un entier) ");
				scanner.nextLine(); // 
				
			}
			
		}
		
		return nbColons; 
	}
    /**
     * Menu intermédiaire qui permet à l'utilisateur de saisir les noms des colons et des ressources,
     * en s'assurant qu'il n'y a pas de doublons.
     *
     * @param nbColons le nombre de colons à créer (il y a le même nombre de colons que de ressources)
     * @return la ColonieSpatiale courante.
     */
	private ColonieSpatiale setNomColonRessource (int nbColons) {
		
		List<Colon> colonie = new ArrayList<>();
		List<Ressources> ensembleRessources = new ArrayList<>();
		
		// Saisie des colons
	    System.out.println("----- Saisie des noms des colons -----");
	    
	    for (int i = 0; i < nbColons; i++) {
	        try {
	            System.out.println("Entrez le nom du colon #" + (i + 1) + " :");
	            String nomColon = scanner.nextLine();

	            // Vérification des doublons pour les colons
	            for (Colon colon : colonie) {
	                if (colon.getNom().equals(nomColon)) {
	                    throw new IllegalArgumentException("Erreur : Le colon '" + nomColon + "' existe déjà.");
	                }
	            }

	            colonie.add(new Colon(nomColon)); // Ajouter le colon

	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	            i--; // Redemander la saisie pour ce colon
	        }
	    }
	    
	    // Saisie des ressources
	    System.out.println("----- Saisie des noms des ressources -----");
	  
	    for (int i = 0; i < nbColons; i++) {
	        try {
	            System.out.println("Entrez le nom de la ressource #" + (i + 1) + " :");
	            String nomRessource = scanner.nextLine();

	            // Vérification des doublons pour les ressources
	            for (Ressources ressource : ensembleRessources) {
	                if (ressource.getNom().equals(nomRessource)) {
	                    throw new IllegalArgumentException("Erreur : La ressource '" + nomRessource + "' existe déjà.");
	                }
	            }


	            ensembleRessources.add(new Ressources(nomRessource));

	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	            i--; // Redemander la saisie pour cette ressource
	        }
	    }
	    
	    ColonieSpatiale colonieSpatiale = new ColonieSpatiale(colonie,ensembleRessources);
	   
	    return colonieSpatiale; 
	}
	
	
	
	
	/**
     * Menu intermédiaire permettant de choisir entre le fait d'ajouter des relations,
     * des préférences pour les colons ou de passer à la seconde étape.
     *
     * @param colonie représentant la colonie spatiale.
     * @return Renvoie le choix de l'utilisateur.
     */
	private int menuAjout(ColonieSpatiale colonie) {
		
		boolean fin = false;
		int reponse = -1;
		
		while (!fin) {
			
			try {
				System.out.println("-----Veuillez choisir votre action-----");
				System.out.println("Entrer 1 pour ajouter une relation entre deux colons");
				System.out.println("Entrer 2 pour ajouter les préférences d'un colon");
				System.out.println("Entrer 3 pour quitter ce menu");
				reponse = scanner.nextInt();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				if (reponse < 1 || reponse >= 4) {
					throw new IllegalArgumentException("Vous devez choisir une action entre 1 et 3");
	
				}
				fin = true;
			}
			catch (IllegalArgumentException e) {
	             System.out.println(e.getMessage());
			}	
			catch(InputMismatchException e) {
				System.out.println("Veuillez rentrer un nombre"); 
				scanner.nextLine(); 
			}
				
		}
		return reponse; 		
		
	}
	
	/**
	 * Mini menu qui permet d'ajouter une relation "n'aime pas" entre deux colons.
	 *
	 * Cette méthode demande à l'utilisateur de fournir les noms de deux colons 
	 * et vérifie leur existence dans la colonie avant d'ajouter la relation.
	 * Si la relation existe déjà, un message est affiché et aucune modification n'est faite.
	 *
	 * @param colonie La colonie contenant les colons à traiter.
	 * @return La colonie spatiale courante
	 */
	
	private ColonieSpatiale menuAjoutRelations(ColonieSpatiale colonie) {
		
		
		String nomPremierColon = null;
		String nomDeuxiemeColon = null;
		Colon colon1 = null;
		Colon colon2 = null;
		boolean ajoutValable = false;
		
		
		while (!ajoutValable) {
			
			try {
				System.out.println("Entrer le nom du premier colon");
				nomPremierColon = scanner.next();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				if (!(colonie.chercheColon(nomPremierColon))) {
					throw new IllegalArgumentException("Le colon (le premier) que vous cherché n'existe pas (nom introuvable)");
				}
				
				System.out.println("Entrez le nom du deuxème colon");
				nomDeuxiemeColon = scanner.next();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				
				if (!(colonie.chercheColon(nomDeuxiemeColon))) {
					throw new IllegalArgumentException("Le colon (le deuxième) que vous cherché n'existe pas (nom introuvable)");
				}
				
				ajoutValable = true;
				
				
			}
			catch (IllegalArgumentException e) {
	             System.out.println(e.getMessage());
			}	
		}
		//On doit maintenant vérifier si les colons ne se détestent pas déjà, il ne faut pas ajouter la relation si elle figure déjà.
		
		colon1 = colonie.getColonParNom(nomPremierColon);
		colon2 = colonie.getColonParNom(nomDeuxiemeColon);
		
		if (colon1.verifAimePas(colon2)) {
			System.out.println(colon1.getNom() + " n'aime déjà pas le colon " + colon2.getNom() + " et inversement");
			System.out.println("Retour vers le menu d'origine");
			
		}
		
		if (!colon1.neSaimePas(colon2)) {
			System.out.println("Le colon " + colon1.getNom() + " ne peut se détester lui même.");
			System.out.println("Retour vers le menu d'origine");
			
		}
		
		return colonie; 
		
		
	}
	
	/**
	 * Mini menu qui ajoute une liste de préférences pour un colon en se basant sur l'entrée utilisateur.
	 * Vérifie que le colon existe et que les ressources spécifiées sont valides.
	 * En cas d'erreur, renvoie l'utilisateur au menu d'ajout.
	 *
	 * @param colonie La colonie contenant les colons et les ressources.
	 * @return La colonie spatiale courante
	 */
	private ColonieSpatiale menuAjoutPreferences(ColonieSpatiale colonie) {
		
		Colon colon = null;
		
		System.out.println("Pour rentrer la liste des préférences pour un colon, veuillez suivre le paterne suivant : ");
		System.out.println("NOM_COLON RESSOURCE1 RESSOURCE 2 ... RESSOURCE N");
		
		try {
			String reponse = scanner.nextLine();
			StringTokenizer reponseDecoupe = new StringTokenizer(reponse," ");
			String nom = reponseDecoupe.nextToken();
			
			colon = colonie.getColonParNom(nom);
			
			if (colon == null) {
				throw new NullPointerException("Le colon avec le nom " + nom + " n'existe pas, vous êtes de retour au menu d'origine");
			}
			
			while (reponseDecoupe.hasMoreTokens()) {
				
				String nomRessource = reponseDecoupe.nextToken();

				Ressources r = colonie.getRessourceParNom(nomRessource);

				if (r==null) {
					colon.laverListePref();				
					throw new NullPointerException("La ressource avec le nom " + nomRessource + " n'existe pas, vous êtes de retour au menu d'origine");

				}
				if (!colon.ajouterPref(r)) {
					colon.laverListePref();
					throw new NullPointerException("La ressource avec le nom " + nomRessource + " est en double dans la liste de préférences, vous êtes de retour au menu d'origine");
				}
			}
			System.out.println("Ajout réussi pour le colon " + colon.getNom());
			
		}
		catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
		
		return colonie; 
		
		
	}
	
	
	/**
	 * Mini menu qui vérifie la validité des préférences des colons dans la colonie.
	 * Si des erreurs sont détectées (doublons, préférences insuffisantes, etc.), 
	 * elles sont affichées, et l'utilisateur est renvoyé au menu d'ajout après nettoyage des listes.
	 *
	 * @param colonie La colonie à vérifier.
	 * @return le nombre correcte pour passer au prochain menu
	 */
	private int menuFin1er(ColonieSpatiale colonie) {
		System.out.println("Vérification des erreurs : ");
		
		// Je ne traîte pas les erreurs ici avec des Exceptions car je veux accumulé les erreurs, si je fais un throw
		// alors on ne détecte qu'une erreur.
		
		List<String> erreurs = new ArrayList<String>();
		
		
		for (Colon colon : colonie.getColonie().values()) {
				
				
			if (colon.getRessourcesPreferes().size() <colonie.getColonie().size() ) {
				erreurs.add("Le colon " + colon.getNom() + " a une liste de préférences insuffisantes : " +  
				colon.getRessourcesPreferes().size() + " au lieu de " + colonie.getColonie().size());
				
			}
		}
		
		
		//On vérifie si on a des erreurs
		
		
		if (!erreurs.isEmpty()) {
			System.out.println("Vous êtes redirigé vers le menu à cause des erreurs suivantes :");
			
			for (String erreur : erreurs) {
	            System.out.println(erreur);
	        }
			
			// Nettoyer les listes de préférences, cela veut dire qu'il doit tout refaire. 
	        for (Colon colon : colonie.getColonie().values()) {
	            colon.laverListePref();
	        }
	        
	        return 0; 
	        
			
		}
		else {
			
			System.out.println("Aucune erreur détecté ");
			System.out.println(colonie);
			System.out.println("Voici l'affection temporaire des ressources pour les colons : ");
			colonie.affectionRessourcesNaive();
			colonie.afficherAffectation();
			
			return 3; 
			
		}
			
	}
	
	
	
	/**
	 * Menu intermédiaire (le 2ème) qui permet de choisir les actions suivantes :
	 * - Échanger des ressources entre deux colons.
	 * - Afficher le nombre de colons jaloux.
	 * - Quitter le menu.
	 *
	 * @param colonie La colonie spatiale.
	 * @return La réponse de l'utilisateur.
	 */
	private int secondMenu(ColonieSpatiale colonie) {
		
		boolean saisieValide = false;
		int reponse = -1;
		
		
		System.out.println("-----Bienvenue dans le second menu-----") ;
		
		while (!saisieValide) {
			
			try {
				System.out.println("-----Veuillez choisir votre action-----");
				System.out.println("Entrer 1 échanger les ressources de deux colons");
				System.out.println("Entrer 2 pour afficher le nombre de colons jaloux");
				System.out.println("Entrer 3 pour quitter ce menu");
				reponse = scanner.nextInt();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				if (reponse < 1 || reponse >= 4) {
					throw new IllegalArgumentException("Vous devez choisir une action entre 1 et 3");
	
				}
				saisieValide = true;
			}
			catch (IllegalArgumentException e) {
	             System.out.println(e.getMessage());
			}	
			catch(InputMismatchException e) {
				System.out.println("Veuillez rentrer un nombre"); 
				scanner.nextLine(); 
			}
			
		}	
		return reponse; 
		
		
		
	}
	
	
	/**
	 * Mini menu qui permet à l'utilisateur d'échanger les ressources entre deux colons.
	 * Vérifie l'existence des colons avant d'effectuer l'échange.
	 * Affiche les affectations après l'échange.
	 *
	 * @param colonie La colonie contenant les colons et leurs ressources.
	 * @return La colonie spatiale courante
	 */
	private ColonieSpatiale menuEchangeRessources(ColonieSpatiale colonie) {
		
		String nomPremierColon = null;
		String nomDeuxiemeColon = null;
		Colon colon1 = null;
		Colon colon2 = null;
		boolean echangeValable = false;
		
		while (! echangeValable) {
			
			try {
				System.out.println("Entrez le nom du premier colon");
				nomPremierColon = scanner.next();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				if (!(colonie.chercheColon(nomPremierColon))) {
					throw new IllegalArgumentException("Le colon (le premier) que vous cherché n'existe pas (nom introuvable)");
				}
				
				System.out.println("Entrez le nom du deuxième colon");
				nomDeuxiemeColon = scanner.next();
				scanner.nextLine(); //On doit consommer le tampon, sinon on reste bloqué.
				
				
				if (!(colonie.chercheColon(nomDeuxiemeColon))) {
					throw new IllegalArgumentException("Le colon (le deuxième) que vous cherché n'existe pas (nom introuvable)");
				}
				
				echangeValable = true;
				
				
			}
			catch (IllegalArgumentException e) {
	             System.out.println(e.getMessage());
			}
				
			
		}
		
		colon1 = colonie.getColonParNom(nomPremierColon);
		colon2 = colonie.getColonParNom(nomDeuxiemeColon);
		colonie.swapRessources(colon1, colon2);
		
		System.out.println("Voici la nouvelle affectation après échange : ");
		colonie.afficherAffectation();
		return colonie; 
		
	}
	
	
	
	/**
	 * Mini menu qui affiche le nombre de colons jaloux dans la colonie.
	 * Redirige ensuite l'utilisateur vers le second menu (2eme menu intermédiaire).
	 *
	 * @param colonie La colonie contenant les colons et les ressources.
	 */
	private void menuAfficherJaloux(ColonieSpatiale colonie) {
		System.out.println("Le nombre de colons jaloux est : " + colonie.calculerJaloux());
		
	}
	
	
	/**
	 * Mini menu qui termine l'exécution du logiciel et ferme le scanner.
	 * @return la réponse correcte pour mettre fin au logiciel.
	 */
	private int menuFin() {
		System.out.println("Fin du logiciel");
		this.scanner.close();
		return 3;

	}
	

}

