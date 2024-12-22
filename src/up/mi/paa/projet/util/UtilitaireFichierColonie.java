package up.mi.paa.projet.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import up.mi.paa.projet.colonie.Colon;
import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.colonie.Ressources;


/**
 * Classe utilitaire permettant de vérifier et de traiter un fichier de configuration pour une colonie spatiale.
 * Elle inclut des méthodes pour lire un fichier, valider sa structure, et enregistrer les données de la colonie.
 */
public class UtilitaireFichierColonie {
	
	
	/**
	 * Attribut statique qui permet de gérer les colons courant pendant l'analyse du fichier.
	 */
	private static List<Colon> colons = new ArrayList<>();
	/**
	 *  Attribut statique qui permet de gérer les ressources courantes pendant l'analyse du fichier.
	 */
	private static List<Ressources> ressources = new ArrayList<>();
	/**
	 *  Attribut statique qui permet de gérer les erreurs courantes pendant l'analyse du fichier.
	 */
	private static List<String> erreurs = new ArrayList<>();
	
	
	 	
	/**
	 * Lit un fichier et retourne une liste des lignes (String).
	 * 
	 * @param cheminFichier le chemin du fichier à lire.
	 * @return Liste des lignes du fichier, ou null si une erreur survient.
	 */
	public static List<String> lireLignesFichier(String cheminFichier) {
	    	
	   List<String> listeDeLignes = new ArrayList<String>();
	    	
	   String ligne = null;
	    	
	   try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
		   
		   while ((ligne = br.readLine()) != null) {
	    			listeDeLignes.add(ligne);
		   }
	    		
	    		
	   }
	   catch (FileNotFoundException e) {
		
		   System.out.println("Erreur : Le fichier '" + cheminFichier + "' n'a pas été trouvé.");
		   return null;
	   
	   }
	   catch (IOException e) {
		   System.out.println("Erreur : Problème d'entrée/sortie lors de la lecture du fichier '" + cheminFichier + "'.");
		   return null;
	   }
	    	
	   return listeDeLignes;
	 
	}
	 
	 
	/**
	 * Vérifie la validité du fichier de configuration d'une colonie spatiale.
	 * Cette méthode valide l'ordonnancement des éléments, le nombre d'éléments,
	 * et vérifie que toutes les relations entre colons et ressources sont correctement définies.
	 * @param cheminFichier le chemin du fichier à vérifier.
	 * @return true si le fichier est valide, sinon false.
	 */
	     
	public static boolean verifierFichier(String cheminFichier) {
		
		List<String> lignes = lireLignesFichier(cheminFichier);
				  
		if (lignes == null) {
			 
			return false; // Si le fichier n'a pas pu être lu, on arrête la vérification
		}
			
		List<Integer> listeVerifOrdonnancement = verifOrdonnancement(lignes);
		
		if (!verifNbColonResPref(lignes)) {
			erreurs.add("Le nombre de colons, ressources et préférences ne coincident pas");
			//return false;
		}
				  
		if (!listeVerifOrdonnancement.isEmpty()) {
			  
			for (int i = 0; i < listeVerifOrdonnancement.size();i++) {
				erreurs.add("Erreur à la ligne " + (listeVerifOrdonnancement.get(i) + 1) + ": L'élement n'est pas placé dans le bon ordre");
			}
			//System.out.println(erreurs);
					 
		}
				  
		
				 
				  
		int numLigne = 1;
				  
				  
		for (String ligne : lignes) {
					  
			if (!ligne.endsWith(".")) {
				erreurs.add("Erreur à la ligne " + numLigne + ": La ligne doit se terminer par un point.");
			}
				  
			if (ligne.startsWith("colon(")) {
				verifierColon(ligne, numLigne);
					
			} else if (ligne.startsWith("ressource(")) {
			   verifierRessource(ligne, numLigne);
						  		  
			} else if (ligne.startsWith("deteste(")) {
			   verifierDeteste(ligne, numLigne);
			        	  
			} else if (ligne.startsWith("preferences(")) {
			   verifierPreferences(ligne, numLigne);
			                
			} else {
				erreurs.add("Erreur à la ligne " + numLigne + ": Format inconnu. (Ne commence pas par un des types par exemple)");
			}  
					  
			numLigne++;  
		}
		
		
				    
		if (!erreurs.isEmpty()) {
					  
			for (String erreur : erreurs) {
				
				System.out.println(erreur);
			}
					
				
			clearData();
			return false;
		}
				  
		clearData();
		return true;
			
	}
	 
	 
	/**
	 * Sauvegarde la solution pour répartir les ressources de la colonie spatiale dans un fichier donné (le chemin).
	 * Chaque colon est écrit avec la ressource qui lui est affectée.
	 * 
	 * @param cs la colonie spatiale à sauvegarder.
	 * @param cheminFichier le chemin du fichier contenant la sauvegarde de la solution.
	 */
	public static void sauvegarderSolutionFichier (ColonieSpatiale cs,String cheminFichier) {
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier)) ){
			 
			for (Colon colon : cs.getColonie().values()) {
				bw.write(colon.getNom() +":"+ colon.getRessourceAffecte().getNom()+"\n");
			}
		} catch (IOException e) {
			System.out.println("Erreur : Problème d'entrée/sortie lors de la l'écriture dans fichier '" + cheminFichier + "'.");
			return;	
		} catch (NullPointerException e) {
			System.out.println("Erreur : veuillez utiliser la résolution automatique avant de vouloir sauvegarder.");
			return;	
		}
		
		System.out.println("Solution enregistré ! :)");
		
	}
	

	
	//--- Méthode privée --//
	 
	 
	 
	/**
	 * Réinitialise les listes de colons, ressources et erreurs.
	 */
	private static void clearData() {
		colons.clear();
		ressources.clear();
		erreurs.clear();
		
	}

	 
	 
	/**
	 * Vérifie la syntaxe d'une ligne définissant un colon.
	 * 
	 * @param ligne la ligne contenant la définition du colon.
	 * @param numLigne le numéro de la ligne dans le fichier.
	 */
	 
	private static void verifierColon(String ligne, int numLigne) {
		
		
		int indexInfoStart = ligne.indexOf('(')+1;
		int indexInfoEnd = ligne.lastIndexOf(')');
		 
		String NomColon = ligne.substring(indexInfoStart,indexInfoEnd);
		 
		if (NomColon.split(",").length > 1) {
			erreurs.add("Erreur à la ligne " + numLigne + ": La ligne 'colon' ne doit contenir qu'un seul nom.");
		}
		else {
			boolean colonExiste = false;
			for (Colon c: colons) {
				 
				if (c.getNom().equals(NomColon)) {
					colonExiste = true;
	                break; 
				}	 
			}
			 
			 
			if (colonExiste) {
				 erreurs.add("Erreur à la ligne " + numLigne + ": Le colon " + NomColon + " est déjà défini (même nom.");
			}
			else {
				 colons.add(new Colon(NomColon));
			}	 
		}		 
		 
	}
	  
	/**
	 * Vérifie la syntaxe d'une ligne définissant une ressource.
	 * 
	 * @param ligne la ligne contenant la définition de la ressource.
	 * @param numLigne le numéro de la ligne dans le fichier.
	 */

	private static void verifierRessource(String ligne, int numLigne) {
		
		int indexInfoStart = ligne.indexOf('(')+1;
		int indexInfoEnd = ligne.lastIndexOf(')');
		 
		String NomRessource = ligne.substring(indexInfoStart,indexInfoEnd);
		 
		if (NomRessource.split(",").length > 1) {
			erreurs.add("Erreur à la ligne " + numLigne + ": La ligne 'ressource' ne doit contenir qu'un seul nom.");
		}
		else {
			
			boolean ressourceExiste = false;
			for (Ressources r: ressources) {
				
				if (r.getNom().equals(NomRessource)) {
					ressourceExiste = true;
	                break; 
				}	 
			}
			 
			 
			if (ressourceExiste) {
				erreurs.add("Erreur à la ligne " + numLigne + ": Le colon " + NomRessource + " est déjà défini (même nom.");
			}
			else {
				ressources.add(new Ressources(NomRessource));
			}	 
		}
		  
	}
	 
	

	/**
     * Vérifie la syntaxe d'une ligne définissant une relation "ne s'aime pas " entre deux colons.
     * 
     * @param ligne la ligne contenant la définition de la relation "ne s'aime pas".
     * @param numLigne le numéro de la ligne dans le fichier.
     */
	private static void verifierDeteste(String ligne, int numLigne) {
		
		
		int indexInfoStart = ligne.indexOf('(')+1;
		int indexInfoEnd = ligne.lastIndexOf(')');
		 
		String nomsColonsBrut = ligne.substring(indexInfoStart, indexInfoEnd);
		String[] nomsColons = nomsColonsBrut.split(",");
		 
		if (nomsColons.length != 2) {
			erreurs.add("Erreur à la ligne " + numLigne + ": La ligne 'deteste' doit contenir exactement deux colons.");
		    return;
		}
		 
		String colon1Nom =	nomsColons[0];
		String colon2Nom = nomsColons[1];
		 
		if (colon1Nom.equals(colon2Nom)) {
			erreurs.add("Erreur à la ligne " + numLigne + ": Un colon ne peut pas se détester lui-même.");
			return;
		}

		 
		Colon colon1 = null;
		Colon colon2 = null;
		 
		for (Colon c : colons) {
			
			if (c.getNom().equals(colon1Nom)) {
				colon1 = c;
		    }
		    if (c.getNom().equals(colon2Nom)) {
		        colon2 = c;
		    }
		}
		 
		if (colon1 == null || colon2 == null) {
			erreurs.add("Erreur à la ligne " + numLigne + ": Les colons " + colon1Nom + " et/ou " + colon2Nom + " ne sont pas définis.");
		    return;
		}
		 
		for (Colon c : colons) {
			if (c.getNom().equals(colon2Nom)) {
				if (c.getAimePas().contains(colon1)) {
					erreurs.add("Erreur à la ligne " + numLigne + ": La relation 'deteste' (" + colon2Nom + " déteste " + colon1Nom + ") a déjà été définie.");
		            return;
		
				}
		    }
		}
		 
		colon1.neSaimePas(colon2);
		
	}
	
	/**
     * Vérifie la syntaxe d'une ligne définissant les préférences d'un colon pour des ressources.
     * 
     * @param ligne la ligne contenant la définition des préférences d'un colon.
     * @param numLigne le numéro de la ligne dans le fichier.
     */
	private static void verifierPreferences(String ligne, int numLigne) {
		
		int indexInfoStart = ligne.indexOf('(')+1;
		int indexInfoEnd = ligne.lastIndexOf(')');
		 
		String preferencesBrut = ligne.substring(indexInfoStart,indexInfoEnd);
		String[] preferences = preferencesBrut.split(",");
		 
		String colonNom = preferences[0];
		Colon colon = null;
		 
		for (Colon c : colons) {
			if (c.getNom().equals(colonNom)) {
		        colon = c;
		        break;
		    } 
		}
		 
		if (colon == null) {
			erreurs.add("Erreur à la ligne " + numLigne + ": Le colon " + colonNom + " dans 'preferences' n'est pas défini.");
		    return;
		}
		 
		if (!colon.getRessourcesPreferes().isEmpty()) {
		    erreurs.add("Erreur à la ligne " + numLigne + ": Les préférences pour le colon " + colonNom + " ont déjà été définies.");
		    return;
		}
		 
		if (preferences.length - 1 != ressources.size()) {
			erreurs.add("Erreur à la ligne " + numLigne + ": Le colon " + colonNom + " doit avoir une préférence pour toutes les ressources (et pas plus également).");
		    return;
		}
		 
		 
		Set<String> ressourcesPrefereesDejaDefinis = new HashSet<>();
		Set<String> ressourcesAjoute = new HashSet<>();
		 
		for (int i = 1; i < preferences.length; i++) {
			 
			String NomRessource = preferences[i].trim();
			 
			Ressources ressource = null;
			 
			for (Ressources r : ressources) {
				if (r.getNom().equals(NomRessource)) {
					ressource = r;
		            break;
		        }
		    }
			 
			if (ressource == null) {
				erreurs.add("Erreur à la ligne " + numLigne + ": La ressource " + NomRessource + " dans 'preferences' n'est pas définie.");
		    }
			else {
				 
				if (!ressourcesPrefereesDejaDefinis.add(NomRessource)) {
					erreurs.add("Erreur à la ligne " + numLigne + ": La ressource " + NomRessource + " est un doublon dans les préférences du colon " + colonNom + ".");
		        }
				else {
					colon.ajouterPref(ressource);
					ressourcesAjoute.add(NomRessource);
				}
				 
				  
			} 
		}
		if (!ressourcesAjoute.containsAll(getRessourcesNoms())) {
			erreurs.add("Erreur à la ligne " + numLigne + ": Le colon " + colonNom + " n'a pas spécifié de préférence pour toutes les ressources existantes.");
		}
	}
	
	
	/**
     * Retourne un ensemble contenant le nom de toutes les ressources définies.
     * 
     * @return Un ensemble contenant les noms des ressources.
     */
	public static Set<String> getRessourcesNoms() {
		
	    Set<String> nomsRessources = new HashSet<>();
	    for (Ressources r : ressources) {
	        nomsRessources.add(r.getNom());
	    }
	    return nomsRessources;
	}
	
	/**
     * Vérifie que les lignes du fichier respectent un ordonnancement spécifique : 
     * colon -> ressource -> deteste -> preferences.
     * 
     * @param lignes la liste des lignes du fichier.
     * @return La liste des indices des lignes problématiques.
     */
	
    public static List<Integer> verifOrdonnancement(List<String> lignes) {
    	
    	String regex = "^c*r*d*p*$";
    	StringBuilder sb = new StringBuilder();
    	List<Integer> indicesLignesProblematiques = new ArrayList<Integer>();

    	for (int i = 0 ;  i <lignes.size(); i++) {
  
    		sb.append(lignes.get(i).charAt(0));
    		
    		if (!sb.toString().matches(regex)) {
    			indicesLignesProblematiques.add(i+1);
    			sb.setLength(sb.length() - 1);
    			
    		}
    	             
    	}
    	
    	
    	return indicesLignesProblematiques;	
    }
    
    /**
     * Vérifie que le nombre de colons, de ressources et de préférences est le même.
     * 
     * @param lignes la liste des lignes du fichier.
     * @return true si le nombre de colons, de ressources et de préférences est le même, sinon false.
     */
    
	public  static boolean verifNbColonResPref (List<String> lignes) {
		
	    int[] countElement = countElements(lignes);
	    	
	    if (countElement[0] <= 0) {
	    	return false;
	    }
	    	
	    return ((countElement[0] == countElement[1]) && (countElement[1] == countElement[3]));
	    	
	}
    
	/**
     * Compte le nombre d'occurrences des éléments "colon", "ressource", "deteste", et "preferences" dans le fichier.
     * 
     * @param lignes la liste des lignes du fichier.
     * @return Un tableau contenant le nombre d'occurrences de chaque élément : 
     * [colon, ressource, deteste, preferences].
     */
	public static int[] countElements (List<String> lignes) {
	    	
		int countC = 0;
		int countR = 0;
		int countD = 0;
		int countP = 0;
		    		
		for (String ligne : lignes) {
			if (ligne.startsWith("c")) {
				countC++;
		    }
		    if (ligne.startsWith("r")) {
		    	countR++;
		    }
		        	
		    if (ligne.startsWith("d")) {
		    	countD++;
		    }
		    if (ligne.startsWith("p")) {
		    	countP++;
		    }
		        		
		}
		int[] listeCount = new int[4];
		listeCount[0] = countC;
		listeCount[1] = countR;
		listeCount[2] = countD;
		listeCount[3] = countP;
		    	
		return listeCount;
	}
			

}
