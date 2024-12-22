package up.mi.paa.projet.menu;

import java.util.Scanner;


/**
 * Classe abstraite représentant un menu générique.
 * Permet d'implémenter différents types de menus en héritant de cette classe.
 */
public abstract class Menu {
	
	/**
     * Scanner utilisé pour la saisie des données utilisateur dans les menus.
     */
	protected Scanner scanner;
	
	
	 /**
     * Constructeur de la classe Menu.
     * Initialise le scanner pour la lecture des entrées utilisateur.
     */
	public Menu() {
		 this.scanner = new Scanner(System.in);
		
	}
	
	 /**
     * Méthode abstraite devant être implémentée par les classes filles.
     * Définit le comportement spécifique lors du démarrage du menu.
     */
	 public abstract void demarrage();
	 
	
}
