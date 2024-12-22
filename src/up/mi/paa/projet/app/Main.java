package up.mi.paa.projet.app;


import up.mi.paa.projet.menu.*;

/*
 * Membre du groupe :
 * SOK VIBOL ARNAUD
 * MEUNIER YOHANN
 * MOUSTACHE MATHIEU
 */

/**
 * Classe principale du logiciel
 */
public class Main {
	
	
	/**
	 * Méthode main qui est le point d'entrée
	 * @param args le tableau d'arguments qui peut être passé
	 */
	public static void main(String[] args) {
		
		
		if (args.length == 0) { 
			Menu menu = new MenuManuel();
			menu.demarrage();
			
		}
		else {  
			Menu menu = new MenuAutomatique(args[0]); 
			menu.demarrage();
			
		}
		
	}
	

}
