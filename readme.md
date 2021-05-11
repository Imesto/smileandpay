# Smile&Pay

##### PostgreSQL

- [Database](#database)
- [Scrip SQL](#script-sql)


##### Java
- [Configuration](#configuration)
- [Installation](#installation)

##### Web service
- [Wsdl](#wsdl)

## PostgreSQL

### Database

Il faut créer une database avec la configuration suivante :
```
database: smileandpay
port: 5432
utilisateur: postgres
mot de passe: sa
```

Si on souhaite modifier ses informations, il faudra par conséquent modifier la configuration [Java](#configuration)

### Script SQL

Dans le [répertoire](src/main/sql) se trouve deux fichiers SQL : 
- [**1.CREATE_TABLE.sql**](src/main/sql/1.CREATE_TABLE.sql) pour la création des tables SQL et des séquences. 
- [**2.ROLLBACK_CREATE_TABLE.sql**](src/main/sql/2.ROLLBACK_CREATE_TABLE.sql) pour revenir en arrière et supprimer les séquences et les tables.

## Java

### Configuration 

La configuration de la base de données se trouve dans le fichier [database.properties](src/main/resources/database.properties) :
 
```
jdbc.driver=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/smileandpay
jdbc.username=postgres
jdbc.password=sa
```

### Installation

Lancer la commande maven :

```
mvn clean install
```

En sortie on obtient un fichier war *smileandpay-testtech.war* qu'il faudra déployer sur tomcat.


## Web Service

### Wsdl

La génération des wsdl se trouve dans le répertoire [bean-wsdl](src/main/resources/bean-wsdl).

Les url des wsdl se trouvent sur http://*url-tomcat*:*port-tomcat*/*nom-du-projet*/*nom-du-wsdl.wsdl*
 
Voici la liste des fichiers wsdl :

- AddMerchantService.wsdl ( Ajout d'un nouveau marchand )
- UpdateMerchantService.wsdl ( Modification d'un marchand existant )
- DeleteMerchantService.wsdl ( Suppression d'un marchand existant )
- AddProductService.wsdl ( Ajout d'un nouveau produit )
- UpdateProductService.wsdl ( Modification d'un produit existant )
- DeleteProductService.wsdl ( Suppression d'un produit existant )
- LinkMerchantProductService.wsdl ( Associé un produit à un marchand )

Exemple : http://localhost:8080/smileandpay-testtech/AddProductService.wsdl


 






