package up.mi.paa.projet.colonie;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * La classe représente une colonie spatiale contenant des colons et des ressources.
 * 
 */
public class ColonieSpatiale {
	
	/**
	 * Map pour représenter la colonie (colons) :
	 * Utiliser une map nous permet un accès rapide à un colon (par sa clé qui est son nom), complexité de O(1).
	 */
	private Map<String, Colon> colonie; 
	
	/**
	 * Map pour représenter l'ensemble des ressources de la colonie :
	 * Utiliser une map nous permet un accès rapide à une ressource (par sa clé qui est son nom), complexité de O(1).
	 */
	private Map<String, Ressources> ensembleRessources; 
	

    /**
     * Ensemble pour représenter les ressources déjà affecté dans la colonie :
     * Nous n'avons pas besoin d'une structure qui est trié donc c'est adapté.
     * Cette approche permet d'optimiser la vérification si la ressource est déjà affectée ou non, complexité O(1).
     * 
     */
	private Set<Ressources> ensembleRessourcesAffectees; 
	
	
	
	/**
     * Constructeur de la classe ColonieSpatiale.
     * Permet de spécifier directement les listes de colons et de ressources.
     *
     * @param colonie la liste des colons (qui sera utilisé pour remplir la Map de colons).
     * @param ensembleRessources la liste des ressources (qui sera utilisé pour remplir la Map de ressources).
     */
	public ColonieSpatiale(List<Colon> colonie, List<Ressources> ensembleRessources) {
		this.colonie = new LinkedHashMap<>();
		this.ensembleRessources = new LinkedHashMap<>();
		this.ensembleRessourcesAffectees = new HashSet<>();
		
		for (Colon c: colonie) {
			this.colonie.put(c.getNom(), c);
		}
		
		for (Ressources r : ensembleRessources) {
			this.ensembleRessources.put(r.getNom(), r);	
		}
	}
	
	
	/**
     * Recherche un colon dans la colonie par son nom.
     *
     * @param nom le nom du colon à rechercher
     * @return true si le colon est trouvé, sinon false
     */
	public boolean chercheColon(String nom) {
		return colonie.containsKey(nom);
	}
	
	/**
     * Récupère un colon dans la colonie par son nom.
     *
     * @param nom le nom du colon
     * @return le colon correspondant ou null si le colon n'est pas trouvé
     */
	public Colon getColonParNom(String nom) {
		return colonie.get(nom);
		
	}
	
	
	/**
     * Récupère une ressource par son nom dans l'ensemble des ressources de la colonie.
     *
     * @param nom le nom de la ressource
     * @return la ressource correspondante ou null si la ressource n'est pas trouvée
     */
	public Ressources getRessourceParNom(String nom) {
		 return ensembleRessources.get(nom);
	}
	
	
	/**
     * Retourne une représentation textuelle de la colonie et des colons qu'elle contient.
     *
     * @return une chaîne de caractères représentant la colonie
     */
	@Override
	public String toString () {
		
		StringBuilder sb = new StringBuilder();
		sb.append("La colonie : \n" );
		
		for (Colon c : colonie.values()) {
			sb.append(c.toString());
		}
		return sb.toString();
	}
	
	
	
	/**
     * Affecte des ressources aux colons en respectant leurs préférences.
     * Chaque ressource ne peut être attribuée qu'à un seul colon.
     * 
     * L'algorithme utilisée est un algorithme naif qui alloue la ressource préférée du colon au colon si elle est encore disponible,
     * sinon on prend la prochaine dans sa liste de préférences et ainsi de suite.
     * 
     * Complexité : O(n^2) (les 2 boucles et .contains est en O(1) car on utilise un HashSet).
     */
	public void affectionRessourcesNaive() { 
		
		for (Colon c : this.colonie.values()) {
			
			if (c.getRessourceAffecte() == null) {
				
				for (Ressources r : c.getRessourcesPreferes().keySet()) {
					if (!this.ensembleRessourcesAffectees.contains(r)) {
						c.setRessourceAffecte(r);
						
						this.ensembleRessourcesAffectees.add(r);
						break;	
					}
					
				}
					
			}
		}	
	}
	
	/**
	 * La méthode permet d'améliorer l'affectation des ressources naive de la partie 1.
	 * Cette méthode tente de minimiser le nombre de colons jaloux en échangeant les ressources entre deux colons
	 * , si cela améliore le nombre de jaloux, alors on garde l'échange, sinon on l'annule.
	 * 
	 * L'amélioration consite à prioriser les colons les plus jaloux.
	 * En effet, cet algorithme se base sur un optimum local, en les priorisant, nous pouvons réduire la valeur du k,
	 * et donc baisser le nombre d'itération et faire tendre la complexité vers O(n^2) au lieu de O(k.n^2).
	 * 
	 * La complexité est en O(k.n^2) , où n est le nombre de colons et k est le nombre maximal d'itérations.
	 * C'est donc un algorithme polynomial.
	 * 
	 * @param k le nombre maximal d'itérations pour tenter d'améliorer l'affectation.
	 */
	public void affectionRessourcesOptimise(int k) {
	    this.affectionRessourcesNaive(); //Complexité O(n^2)
	    int coutActuel = this.calculerJaloux(); //Complexité O(n^2)

	    int i = 0;

	    Collection<Colon> colonCollection = colonie.values(); // Utiliser une collection directement car on ne peut utiliser une Map.
	    Random random = new Random(); // Générateur pour choisir le deuxième colon parmis ceux que le premier colon n'aime pas.

	    while (i < k) {

	        // Trouver un colon avec un nombre de jaloux élevé
	        Colon premierColon = null;
	        int maxJalousie =  Integer.MIN_VALUE; 
	        for (Colon colon : colonCollection) {
	            int jalousieColon = colon.getAimePas().size(); 
	            if (jalousieColon >  maxJalousie) {
	                maxJalousie = jalousieColon;
	                premierColon = colon;
	            }
	        }


	        // On recherche un colon avec qui échanger, parmi ceux que le premier colon n'aime pas
	        Set<Colon> premierColonAimePas = premierColon.getAimePas();
	        if (premierColonAimePas.isEmpty()) {
	            i++;
	            continue;
	        }
	        
	        // Sélectionner un colon aléatoire à partir du Set
	        int randomIndex = random.nextInt(premierColonAimePas.size());
	        Colon deuxiemeColon = null;
	        int index = 0;
	        
	        for (Colon colonAimePas : premierColonAimePas) {
	            if (index == randomIndex) {
	                deuxiemeColon = colonAimePas;
	                break;
	            }
	            index++;
	        }


	        // On Échange les ressources
	        this.swapRessources(premierColon, deuxiemeColon);

	        // Calculer le nouveau coût
	        int coutNouveau = calculerJaloux(); //O(n^2)

	        // Si le nouveau coût est meilleur, on conserve la solution
	        if (coutNouveau < coutActuel) {
	            coutActuel = coutNouveau;
	        } else {
	            // Sinon, on annule l'échange
	            this.swapRessources(premierColon, deuxiemeColon);
	        }

	        i++; 
	    }
	    
	}
	
	/**
    * Affiche les affectations des ressources des colons.
    */
	public void afficherAffectation() {
		for (Colon colon : this.colonie.values()) {
			System.out.println(colon.getNom() + " : " + colon.getRessourceAffecte().getNom());
		}
	}
	
	 /**
     * Échange les ressources affectées entre deux colons.
     *
     * @param colon1 le premier colon
     * @param colon2 le second colon
     */
	public void swapRessources(Colon colon1, Colon colon2) {
		
		Ressources tmp = colon1.getRessourceAffecte();
		colon1.setRessourceAffecte(colon2.getRessourceAffecte());
		  
		colon2.setRessourceAffecte(tmp);
		
	}
	
	
	/**
     * Affiche et retourne le nombre de colons jaloux.
     * Un colon est jaloux si un colon qu'il n'aime pas possède une ressource qu'il préfère d'avantage
     * que celle qui lui a été attribuée.
     * 
     * La complexité est de O(n^2)
     *
     * @return le nombre de colons jaloux
     */
	public int calculerJaloux() {
		
		int nbColonsJaloux = 0;
		
		// Pour chaque colon dans la colonie
		for (Colon colon : colonie.values()) { //O(n)
			
			if (colon.getAimePas() == null) continue;

	
			int indiceResColonCourant = colon.getIndiceByRessources(colon.getRessourceAffecte());  //O(1)
			
			
			// Pour chaque colon que ce colon n'aime pas
			for  (Colon colonAimePas : colon.getAimePas()) { //Au max O(n-1)
				
				if (indiceResColonCourant > colon.getIndiceByRessources(colonAimePas.getRessourceAffecte())) { //O(1) pour getIndiceByRessources
				
					nbColonsJaloux++;
					break;
				}	
			}	
		}

		return nbColonsJaloux;
	}
	
	// Getters et setters
	
	
	/**
     * Récupère la liste des ressources déjà affectées.
     *
     * @return la liste des ressources affectées
     */
	public Set<Ressources> getEnsembleRessourcesAffectees() {
		return ensembleRessourcesAffectees;
	}

	/**
     * Définit la liste des ressources affectées.
     *
     * @param ensembleRessourcesAffectees la nouvelle liste des ressources affectées
     */
	public void setEnsembleRessourcesAffectees(Set<Ressources> ensembleRessourcesAffectees) {
		this.ensembleRessourcesAffectees = ensembleRessourcesAffectees;
	}


	/**
     * Récupère la liste (plus préscisement une Map) des colons dans la colonie.
     *
     * @return la map des colons
     */
	public Map<String, Colon> getColonie() {
		return colonie;
	}
	

	
	/**
     * Récupère la liste (plus préscisement une Map) des ressources dans la colonie.
     *
     * @return la map des ressources
     */
	public  Map<String, Ressources> getEnsembleRessources() {
		return ensembleRessources;
	}
	

}
