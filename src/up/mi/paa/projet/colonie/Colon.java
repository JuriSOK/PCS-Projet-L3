package up.mi.paa.projet.colonie;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * La classe représente un colon dans une colonie spatiale, avec des préférences sur les ressources,
 * des relations avec d'autres colons, et une ressource qui lui est attribuée.
 */

public class Colon {
	
	/**
	 * Nom du colon (son identifiant).
	 */
    private String nom;
    
    
    /**
     * Map pour représenter ses préférences :
     * La clé est la ressource (chaque ressource est unique donc nous pouvons procéder comme ceci)
     * La valeur est l'indice de préférence (0 pour sa préféré).
     * Cette approche nous permet d'optimiser les recherches, complexité O(1).
     * 
     * @param nom le nom du colon
     */
    private Map<Ressources, Integer> ressourcesPreferes; 
    
    /**
     * Ensemble pour représenter les colons qu'il n'aime pas :
     * Nous n'avons pas besoin d'une structure qui est trié donc c'est adapté.
     * Cette approche permet d'optimiser la vérification si il y'a un colon ou non, complexité O(1).
     * 
     */
    private Set<Colon> aimePas;  
   
    /**
     * La ressource qu'il lui est affecté.
     */
    private Ressources ressourceAffecte;
    
    /**
     * Compteur pour permettre d'incrémenter l'indice de préférence pour la Map.
     */
    private int compteurPreference;
    
    
    
    
    /**
     * Constructeur de la classe Colon.
     *
     * @param nom le nom du colon a créer.
     */
    public Colon(String nom) {
    	
        this.nom = nom;
        this.ressourcesPreferes = new LinkedHashMap<>(); // Nous devons maintenir un ordre de préférence, donc nous devons posséder une structure avec un ordre.
        this.aimePas = new HashSet<Colon>();
        this.ressourceAffecte = null;
        this.compteurPreference = 0; // Initialisation du compteur d'indices
    }
    
    
    /**
     * Ajoute un colon à la liste des colons que le colon courant n'aime pas.
     *
     * @param c1 le colon à ajouter dans la liste des colons que le colon courant n'aime pas.
     * @return true si on a réussit à ajouter, faux sinon.
     */
    public boolean neSaimePas(Colon c1){
    	
    	if (this == c1) {
    		return false;
    	}
        this.aimePas.add(c1);
        c1.getAimePas().add(this);
        return true;
    }
    
    
    /**
     * Ajoute une ressource à la liste de préferences  du colon courant.
     *
     * @param r la ressource à ajouter
     * @return true si on a réussit à ajouter, faux sinon.
     */
    public boolean ajouterPref(Ressources r){
    	
    	if (!ressourcesPreferes.containsKey(r)) {
    		ressourcesPreferes.put(r, compteurPreference++);
    		return true;
    	}
 
        return false;
    }
    
    
    /**
     * Vérifie si le colon courant n'aime pas un autre colon donné.
     *
     * @param colon le colon à vérifier
     * @return true si le colon donné est dans la liste des colons que le colon courant n'aime pas, sinon false
     */
    public boolean verifAimePas(Colon colon) {
    	 return this.aimePas.contains(colon);
    }
    
  
    /**
     * Vide la liste des préférences du colon courant.
     */
    public void laverListePref() {
    	this.getRessourcesPreferes().clear();
    }
    
    
    /**
     * Récupère l'indice d'une ressource spécifique dans la liste des préférences du colon courant.
     *
     * @param ressource la ressource à rechercher
     * @return l'indice de la ressource si elle est trouvée, sinon -1
     */
    public int getIndiceByRessources(Ressources ressource) {
    	
    	return ressourcesPreferes.getOrDefault(ressource, -1); // Retourne -1 si la ressource n'est pas trouvée
    }
    
    /**
     * Représente le colon courant sous forme de chaîne de caractères.
     *
     * @return une représentation textuelle du colon
     */
    @Override
    public String toString() {
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Nom colon : " + nom +"\n");
    	sb.append("Il n'aime pas : ");
    	
    	for (Colon colon : this.getAimePas()) {
    		sb.append(colon.getNom() +" ");
    	}
    	sb.append("\n");
    	sb.append("Voici sa liste de préférences, du plus aimé au moins aimé : \n");
    	
    	for (Ressources res : this.ressourcesPreferes.keySet()) {
    		sb.append(res.getNom()+ " ");
    	}
    	sb.append("\n");
    	return sb.toString();
 
    }
    
    // Getters et setters
    
    
    /**
     * Récupère l'ensemble des préférences du colon courant
     *
     * @return la hashmap (ressource, indice de préférence) du colon courant
     */
	public  Map<Ressources, Integer> getRessourcesPreferes() {
		return ressourcesPreferes;
	}

	
	 /**
     * Définit la hashmap des préférences du colon courant
     *
     * @param ressourcesPreferes le nouveau ensemble des préférences
     */
	public void setRessourcesPreferes(Map<Ressources, Integer> ressourcesPreferes) {
		this.ressourcesPreferes = ressourcesPreferes;
	}



	/**
     * Récupère la ressource actuellement attribuée au colon courant
     *
     * @return la ressource attribuée au colon courant
     */
	public Ressources getRessourceAffecte() {
		return ressourceAffecte;
	}



	/**
     * Définit la ressource attribuée du colon courant
     *
     * @param ressourceAffecte la ressource à attribuer
     */
	public void setRessourceAffecte(Ressources ressourceAffecte) {
		this.ressourceAffecte = ressourceAffecte;
	}

	/**
     * Récupère le nom du colon courant
     *
     * @return le nom du colon courant
     */
	public String getNom() {
		return nom;
	}

	 /**
     * Définit le nom du colon courant
     *
     * @param nom le nouveau nom du colon
     */
	public void setNom(String nom) {
		this.nom = nom;
	}

	
	/**
     * Récupère l'ensemble des colons que le colon courant n'aime pas.
     *
     * @return l'ensemble des colons non aimés
     */
	public Set<Colon> getAimePas() {
		return aimePas;
	}

	
	/**
     * Définit l'ensemble des colons que le colon courant n'aime pas.
     *
     * @param aimePas l'ensemble des colons non aimés
     */
	public void setAimePas(Set<Colon> aimePas) {
		this.aimePas = aimePas;
	}
		
	
 
}
