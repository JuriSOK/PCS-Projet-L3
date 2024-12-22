# README du Projet

## Description du Projet

L'objectif du projet est de concevoir un logiciel permettant de résoudre un problème d'affectation de ressources entre des colons d'une colonie spatiale. L'utilisateur peut choisir d'entrer lui-même les colons et les ressources de la colonie, puis affecter les relations de préférences, les ressources, etc. via le **CLI** intégré.

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

  Pour exécuter le programme avec un fichier de données, l'utilisateur peut procéder comme suit (ou utiliser un IDE) :

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

La classe principale pour exécuter le programme est **`Main.java`**. Cette classe contient la méthode `main()` qui démarre l'exécution du programme. Elle est située dans le package **`up.mi.paa.projet.app`**.

## Algorithme de Résolution

### Méthode `affectionRessourcesOptimise(int k)`

L'algorithme est utilisé dans la méthode **`affectionRessourcesOptimise(int k)`** dans la classe **`ColonieSpatiale.java`** dans le package **`up.mi.paa.projet.colonie`**. Voici le fonctionnement de l'algorithme :

1. **Initialisation** : L'algorithme commence par appeler la méthode **`affectionRessourcesNaive()`** pour initialiser l'affectation des ressources, avec une complexité de **O(n²)**.

2. **Calcul du Coût Actuel** : Le coût de cette affectation naîve est calculé avec la méthode **`calculerJaloux()`**, également de complexité **O(n²)**.

3. **Boucle Principale** : L'algorithme procède à **k** itérations (où **k** est un paramètre de méthode) :
    - Il sélectionne le colon le plus jaloux de la colonie (*c'est l'heuristique que nous avons décidé de choisir*).
    - Il choisit un colon parmi ceux que ce colon n’aime pas, et échange leurs ressources.
    - Après chaque échange, le coût est recalculé avec **`calculerJaloux()`**.
    - Si l’échange améliore le coût, il est validé. Sinon, il est annulé.


### Pseudo-code

```pseudo
ALGORITHME affectionRessourcesOptimise(k)
    // Initialiser une affectation naïve des ressources
    affectionRessourcesNaive()
    coutActuel ← calculerJaloux()

    i ← 0

    // Récupérer tous les colons de la colonie
    colonCollection ← ensemble_es_colons()
    générateur_aléatoire ← nouvel_objet_aléatoire()

    TANT QUE (i < k) FAIRE
        // Trouver le colon ayant le plus de jaloux
        colonLePlusJaloux ← null
        maxJalousie ← -∞
        POUR chaque colon DANS colonCollection FAIRE
            jalousieColon ← nombre_de_colons_que(colon)_n'aime_pas()
            SI (jalousieColon > maxJalousie) ALORS
                maxJalousie ← jalousieColon
                colonLePlusJaloux ← colon
            FIN SI
        FIN POUR

        // Obtenir la liste des colons que colonLePlusJaloux n'aime pas
        colonsAimesPas ← liste_des_colons_que(colonLePlusJaloux)_n'aime_pas()

        // Si la liste est vide, passer à la prochaine itération
        SI (colonsAimesPas EST VIDE) ALORS
            i ← i + 1
            CONTINUER
        FIN SI

        // Choisir un colon aléatoire parmi ceux que colonLePlusJaloux n'aime pas
        indiceAléatoire ← générateur_aléatoire.entier_entre(0, taille(colonsAimesPas) - 1)
        colonÉchangé ← élément_à_l'indice(indiceAléatoire, colonsAimesPas)

        // Échanger les ressources entre les deux colons
        échangerRessources(colonLePlusJaloux, colonÉchangé)

        // Recalculer le coût après l'échange
        nouveauCout ← calculerJaloux()

        // Si le nouveau coût est meilleur, conserver l'échange
        SI (nouveauCout < coutActuel) ALORS
            coutActuel ← nouveauCout
        SINON
            // Sinon, annuler l'échange
            échangerRessources(colonLePlusJaloux, colonÉchangé)
        FIN SI

        i ← i + 1
    FIN TANT QUE
FIN ALGORITHME
```

### Complexité

- La complexité globale de l'algorithme est **O(k * n²)**, où **k** est le nombre d'itérations et **n** le nombre de colons. Cette complexité est dominée par la méthode **calculerJaloux()**, qui est en **O(n²)**.


## Comparaison avec l'Algorithme Naïf

L'algorithme optimisé proposé (**`affectionRessourcesOptimise(int k)`**) présente des améliorations significatives par rapport l'algorithme proposée dans le sujet décrit ci-dessous.

### Algorithme Naïf
L'algorithme naïf suit une stratégie simple et se base sur des choix aléatoires :
1. Il commence par une solution initiale obtenue via une affectation naïve *(Comme dans notre algorithme)*.
2. À chaque itération, il sélectionne aléatoirement un colon et un voisin parmi ceux qu’il n’aime pas.
3. Les ressources des deux colons sont échangées, et si cet échange améliore la solution, il est conservé ; sinon, il est annulé.
4. Ce processus est répété pour un nombre maximum d’itérations **k**.

### Limites de l'Algorithme Naïf
- **Choix aléatoire inefficace** : Les colons problématiques (ceux détestant beaucoup de colons) ne sont pas priorisés. Cela peut entraîner de nombreux échanges peu pertinents.
- **Convergence lente** : En raison de la sélection aléatoire, l’algorithme peut nécessiter un grand nombre d’itérations pour obtenir une solution acceptable, ce qui augmente la complexité effective.

### Pourquoi notre algorithme Optimisé est Meilleur ?
L'algorithme optimisé améliore la stratégie naïve de plusieurs manières :
1. **Priorisation des colons problématiques** : Notre algorithme optimisé utilise une **heuristique** ; il identifie les colons qui détestent beaucoup de colons et concentre les échanges sur ces individus. Cela maximise l’impact de chaque itération.
2. **Réduction du nombre d’itérations nécessaires** : En choisissant ses colons en priorité, l’algorithme peut atteindre une solution acceptable avec un nombre d’itérations **k** réduit. Cela réduit donc la complexité effective.
3. **Convergence plus rapide** : L'algorithme optimisé tend vers un optimum local de manière plus efficace, en limitant les échanges inutiles.

Notre algorithme optimisé est donc plus intelligent dans sa manière de résoudre le problème, avec une stratégie axée sur les priorités plutôt que sur des choix aléatoires. Cela se traduit par une meilleure performance et une solution de qualité en moins de temps.


## Fonctionnalités Implémentées

- **Logicel en mode intéractif via le CLI** : Notre logicel permet à l'utilisateur de réaliser solution complète manuellement, c'est à dire :
1. Choisir le nombre de colons (le même que le nombre de ressources) et définir leurs noms.
2. Définir les relations entre les colons, et leurs préférences.
3. Pouvoir échanger leurs ressources, puis afficher le nombre de colons jaloux dans cette configuration.


- **Logicel en mode automatique grâce à un fichier de configuration** :

1. Pouvoir donner un fichier en argument, qui défini la colonie spatiale.
2. Gestion des erreurs de formats du fichiers robuste, et indique les problèmes de toutes les lignes problématiques.
3. Pouvoir réaliser une affectation automatique des ressources de la colonie spatiale via un algorithme optimisé.
4. Pouvoir enregistrer la solution dans un fichier que l'utilisateur choisit, ou laisser le logiciel lui créer un fichier dans un répertoire dédié pour si l'utilisateur n'a pas de fichier disponible.



