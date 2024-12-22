package up.mi.paa.projet.util;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import up.mi.paa.projet.colonie.Colon;
import up.mi.paa.projet.colonie.ColonieSpatiale;
import up.mi.paa.projet.colonie.Ressources;


/**
 * Classe utilitaire permettant de créer une instance de ColonieSpatiale
 * à partir d'un fichier de configuration de ce format : 
 * colon(nom_colon_1).
 * colon(nom_colon_2).
 * colon(nom_colon_3).
 * ressource(nom_ressource_1).
 * ressource(nom_ressource_2).
 * ressource(nom_ressource_3).
 * deteste(nom_colon_1,nom_colon_2).
 * deteste(nom_colon_2,nom_colon_3).
 * preferences(nom_colon_1,nom_ressource_1,nom_ressource_2,nom_ressource_3).
 * preferences(nom_colon_2,nom_ressource_2,nom_ressource_1,nom_ressource_3).
 * preferences(nom_colon_3,nom_ressource_3,nom_ressource_1,nom_ressource_2).
 */
public class ColonieFactory {
	
	/**
     * Crée une instance de ColonieSpatiale à partir d'un fichier donné.
     * Le fichier est vérifier à l'intérieur de la méthode via une méthode de vérification.
     * 
     * Si le fichier est invalide ou introuvable, la méthode retourne null.
     * @param cheminFichier qui est le chemin du fichier de configuration.
     * @return Une instance de ColonieSpatiale si le fichier est valide, sinon null.
     */
	public static ColonieSpatiale creeColonieSpatialeParFichier(String cheminFichier) {
		
		
		if (!UtilitaireFichierColonie.verifierFichier(cheminFichier)) {
			return null;
		}
		
		List<Colon> colons = new ArrayList<>();
		List<Ressources> ressources = new ArrayList<>();
		
		// Dictionnaires pour un accès rapide aux objets Colon et Ressources, pour les deteste et pref
	    Map<String, Colon> colonMap = new HashMap<>();
	    Map<String, Ressources> ressourceMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {

			 String line;
			 
			 while ((line = br.readLine()) != null ) {
				 
				 if (line.startsWith("colon")) {
					 
					 String nomColon = line.substring(line.indexOf("(")+1,line.lastIndexOf(")"));
					  Colon colon = new Colon(nomColon);
					  colons.add(colon);
					  colonMap.put(nomColon, colon);
				 }
				 
				 else if (line.startsWith("ressource")) {
					 String nomRessource = line.substring(line.indexOf("(")+1,line.lastIndexOf(")"));
					 Ressources ressource = new Ressources(nomRessource);
					 ressources.add(ressource);
		             ressourceMap.put(nomRessource, ressource);
					 
				 } 
				 else if (line.startsWith("deteste")) {
					 
					 String[] noms = line.substring(line.indexOf("(")+1,line.lastIndexOf(")")).split(",");
					 Colon colon1 = colonMap.get(noms[0]);
		             Colon colon2 = colonMap.get(noms[1]);
		             colon1.neSaimePas(colon2);
					 
				 }
				 else if (line.startsWith("preferences")) {
					 String[] arguments = line.substring(line.indexOf("(")+1,line.lastIndexOf(")")).split(",");
					 Colon colon = colonMap.get(arguments[0]);
					 
					 for (int i = 1; i < arguments.length; i++) {
						 Ressources ressource = ressourceMap.get(arguments[i]);
						 colon.ajouterPref(ressource);
	                     
	                 }
					 
				 }	 
				 
			 }
					
			
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : Le fichier '" + cheminFichier + "' n'a pas été trouvé.");
			return null;
		} catch (IOException e) {
			System.out.println("Erreur : Problème d'entrée/sortie lors de la lecture du fichier '" + cheminFichier + "'.");
			return null;
		}
		
		
		return new ColonieSpatiale(colons, ressources);
	}
	
	
	
	

}
