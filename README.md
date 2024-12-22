
# README du Projet

## Description du Projet

Le projet consiste à concevoir un logiciel permettant de résoudre un problème d'affectation de ressources entre des colons d'une colonie spatiale. L'utilisateur peut choisir d'entrer lui-même les colons et les ressources de la colonie, puis affecter les relations de préférences, les ressources, etc. via le CLI intégré.

Deux options s'offrent à l'utilisateur :
1. **Mode interactif** : L'utilisateur peut saisir manuellement les colons, les ressources et leurs relations via une interface en ligne de commande (CLI).
2. **Mode automatique** : L'utilisateur peut fournir un fichier au format suivant pour automatiser l'entrée des données :

    ```text
    colon(nom_colon_1).
    colon(nom_colon_2).
    colon(nom_colon_3).
    ressource(nom_ressource_1).
    ressource(nom_ressource_2).
    ressource(nom_ressource_3).
    deteste(nom_colon_1, nom_colon_2).
    deteste(nom_colon_2, nom_colon_3).
    preferences(nom_colon_1, nom_ressource_1, nom_ressource_2, nom_ressource_3).
    preferences(nom_colon_2, nom_ressource_2, nom_ressource_1, nom_ressource_3).
    preferences(nom_colon_3, nom_ressource_3, nom_ressource_1, nom_ressource_2).
    ```

  Pour exécuter le programme avec un fichier de données, l'utilisateur devra procéder comme suit :

  1. Compiler le programme :
     ```bash
     javac MonProgramme.java
     ```

  2. Lancer le programme avec le chemin du fichier en argument :
     ```bash
     java MonProgramme <chemin_du_fichier>
     ```

L'objectif principal est d'optimiser l'affectation des ressources en minimisant un coût, défini par la **jalousie** entre les colons. Un colon est jaloux si l'un des colons qu'il n'aime pas possède une ressource qu'il aurait aimé avoir.

## Classe Principale

La classe principale pour exécuter le programme est **`Main.java`**. Cette classe contient la méthode `main()` qui démarre l'exécution du programme.

## Algorithme de Résolution

### Méthode `affectionRessourcesOptimise(int k)`

L'algorithme principal est la méthode **`affectionRessourcesOptimise(int k)`** qui optimise l'affectation des ressources. Voici le fonctionnement de l'algorithme :

1. **Initialisation** : L'algorithme commence par appeler la méthode **`affectionRessourcesNaive()`** pour initialiser l'affectation des ressources, avec une complexité de **O(n²)**.

2. **Calcul du Coût Actuel** : Le coût de l'affectation (basé sur le nombre de jalousies) est calculé avec la méthode **`calculerJaloux()`**, également de complexité **O(n²)**.

3. **Boucle Principale** : L'algorithme procède à **k** itérations (où **k** est un paramètre) :
    - Il sélectionne un colon ayant un nombre élevé de jaloux.
    - Il choisit un colon parmi ceux que ce colon n'apprécie pas, et échange leurs ressources.
    - Après chaque échange, le coût est recalculé.
    - Si l'échange améliore le coût, il est validé. Sinon, il est annulé.

4. **Objectif** : L'algorithme vise à réduire la jalousie totale en minimisant le coût de l'affectation, en échangeant des ressources entre les colons.

### Complexité

- La complexité globale de l'algorithme est **O(k * n²)**, où **k** est le nombre d'itérations et **n** le nombre de colons. Cette complexité est dominée par la méthode **`calculerJaloux()`**, qui est en **O(n²)**.

## Fonctionnalités Implémentées

- **Affectation Initiale des Ressources** : La méthode **`affectionRessourcesNaive()`** effectue une affectation initiale des ressources de manière naïve.
- **Optimisation de l'Affectation** : La méthode **`affectionRessourcesOptimise(int k)`** optimise l'affectation des ressources en réduisant la jalousie globale par des échanges de ressources.
- **Calcul du Coût de l'Affectation** : La méthode **`calculerJaloux()`** calcule le coût en fonction des relations de jalousie entre les colons.

## Fonctionnalités Manquantes ou Problèmes

- **Performance** : L'algorithme reste relativement coûteux pour un grand nombre de colons (**n** élevé). Il serait possible d'optimiser la recherche des colons à échanger ou d'utiliser des heuristiques supplémentaires pour améliorer la performance.
- **Affichage des Résultats** : Le programme ne propose pas encore de fonctionnalité d'affichage des résultats optimisés après l'exécution. L'ajout d'une telle fonctionnalité (par exemple, afficher l'affectation finale ou les étapes intermédiaires) serait une amélioration souhaitée.

## Auteurs

Ce projet a été réalisé par les membres suivants :
- **Nom de l'auteur 1**
- **Nom de l'auteur 2**
- **Nom de l'auteur 3**
