# Projet P9
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=Asediab_myerp&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=Asediab_myerp)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=Asediab_myerp&metric=alert_status)](https://sonarcloud.io/dashboard?id=Asediab_myerp) 
[![Build Status](https://travis-ci.com/Asediab/myerp.svg?branch=master)](https://travis-ci.com/Asediab/myerp)

## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up
    
##Test
###Maven profiles
* sonar - lancement de tous tests unitaires et IT (profil par défaut)
* unitTest - lancement de tous tests unitaires
* test-business - lancement des tests d'intégrations de module business
* test-consumer - lancement des tests d'intégrations de module consumer
###Lancement de Test
En utilisant Maven command test avec balise -P pour spécifier le profil. ` mvn test -P sonar ` 
###Lancement d'analyse SonarCloud
 ` mvn verify sonar:sonar` 

#Correctifs
*   ` com.dummy.myerp.model.bean.comptabilit.EcritureComptable`
    * correction de la méthode `getTotalCredit()` qui accédait à la méthode `getDebit()` au lieu de `getCredit()`
    * correction de la méthode `isEquilibree()` qui retournait le résultat d'une égalité à l'aide de `equals()` au lieu de faire une comparaison avec `compareTo()
*   `com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl`
    * correction de la méthode `updateEcritureComptable()`. Ajouter la ligne `this.checkEcritureComptable(pEcritureComptable);` afin de vérifier que la référence de l'écriture comptable respecte les règles de comptabilité 5 et 6
*   `sqlContext.xml`, correction la propriété `SQLinsertListLigneEcritureComptable`, où manquait une virgule dans le INSERT entre les colonnes `debit` et `credit`
