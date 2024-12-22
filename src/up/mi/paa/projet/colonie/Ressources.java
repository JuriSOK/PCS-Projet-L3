package up.mi.paa.projet.colonie;


/**
 * La classe représente une ressource qui sera affectée à un des colons de la colonie spatiale.
 */
public class Ressources {
	
	
	/**
	 * Une ressource est identifiée par son nom.
	 */
    private String nom;
   
    
    /**
     * Constructeur de la classe Ressources.
     *
     * @param nom le nom de la ressource
     */
    public Ressources(String nom) {
        this.nom = nom;

    }
    
    // Getters et setters
    
    /**
     * Récupère le nom de la ressource.
     *
     * @return le nom de la ressource
     */
	public String getNom() {
		return nom;
	}
	
	/**
     * Définit le nom de la ressource.
     *
     * @param nom le nouveau nom de la ressource
     */
	public void setNom(String nom) {
		this.nom = nom;
	}
    
	
	/**
     * Vérifie si deux objets Ressources sont égaux.
     * Deux ressources sont considérées égales si elles ont le même nom.
     *
     * @param o l'objet à comparer
     * @return true si les deux ressources ont le même nom, sinon false
     */
	@Override
	public boolean equals (Object o) {
		
		  if (this == o) {
			  return true;
		  }
			  
		  else if (o == null || getClass() != o.getClass()) {
			  return false;
		  }
			  
		  
		  Ressources ressource = (Ressources)o;
		  
		  return this.nom.equals(ressource.getNom());
		
	}

}
