# Projet LEE - Articles de presse

## Objectif
Le but de se projet est de mettre en avant les technologies suivantes:
* Swagger: decrire une API REST
* Oauth2: gerer la securite des appels REST
* Spring-boot: implementer un service REST
* Spring-data: faire le mapping entre Java et la BD


## Code source
https://github.com/wafae-oufkir/articles_presse_backend-wafae

## Pre-requis
Les outils suivants sont necessaires pour demarrer l'application:
* Java (JDK8)
* Maven
* Mongodb


## Technologies
### Swagger
https://swagger.io/
Swagger est un outil pour decrire une API.
Parmis les fonctionnalites qu'offrent Swagger, ce projet utilise la generation de code via un plugin maven.
L'API de notre service REST est decrite dans le fichier resource-server/src/main/resources/api.yaml.
Lors de la build maven le code correspondant est genere dans le dossier
resource-server/target/generated-sources/swagger/src/gen/java/main/io/swagger.

### Spring-boot
http://spring.io/projects/spring-boot
Spring-boot est un framework Java permettant de creer un service REST avec quelques annotations.


### Spring-data
https://spring.io/projects/spring-data
Spring-data est un framework Java permettant d'acceder a de nombreuses base de donnees.
Ce projet utilise spring-data pour acceder a une base mongodb.

### Oauth2
https://tools.ietf.org/html/rfc6749
Oauth2 est un protocole utilise pour obtenir un token afin de securiser des requetes HTTP.


## Installation
### Mongodb
Une instance de mongodb doit ecouter sur le port local 27017.
Cette instance peut s'obtenir via docker par la commande suivante:
```
docker run --rm -d -p 27017:27017 mongo
```

### Compilation
Depuis le dossier source du projet:
```
mvn clean install -DskipTests
```

### Lancement de l'authorization server
Depuis le dossier source du projet:
```
mvn spring-boot:run -f authorization-server/
```

### Lancement du resource server
Depuis le dossier source du projet:
```
mvn spring-boot:run -f resource-server/
```


## Utilisation
Ouvrir un navigateur web et aller a la page https://localhost:8443/login.
Le resource server utilise HTTPS avec un certificat self-signed, le navigateur web demande donc confirmation avant
d'acceder a la page.
Ajouter une exception dans le navigateur web afin d'acceder au site web.
Le navigateur web est alors redirige vers l'authorization server
https://localhost:8888/oauth/authorize?response_type=code&client_id=lee&redirect_uri=https://localhost:8443/code.
L'authorization server utilise lui aussi HTTPS, il faut donc a nouveau ajouter une exception dans le navigateur web.
Le navigateur web accede a la page d'authentification sur l'authorization server.
Le nom d'utilisateur est foo et le mot de passe est foo.
Apres avoir saisit les identifiants, la page de consentement de l'authorization server s'affiche.
Apres avoir consentit a l'acces au resource server, le navigateur est redirige vers celui-ci.
La page comporte une table contenant les articles presents en BD et un formulaire ainsi qu'un bouton pour creer un
nouvel article.


## Fonctionnement
### Securite
La securite du service REST (resource server) repose sur deux aspects:
* la presence d'un token dans chaque requete
* la protection de ce token
Le token est obtenu avec le flow Oauth2 Authorization Code en echange des identifiants fournit a l'authorization server.
La protection de l'access-token repose sur l'utilisation de HTTPS.

### Resource server
Le resource server possede plusieurs controlleurs.
* /login: puisque l'autentification est geree par l'authorization server, ce controlleur redirige vers l'authorization
server.

* /code: a partir d'un code a usage unique fournit par l'authorization server le controlleur se charge d'obtenir un
access token puis de le stocker en session.
La reponse renvoie ainsi un cookie JSESSIONID=<>.
La reponse HTTP est un redirect vers la page /index.html.
Via la classe AuthorizationFilter, chaque requete contenant ce cookie est enrichi d'un header Authorization comportant
l'access-token stocke en session.

* /articles: implemente le service REST.

### Web page
La seule page HTML est /index.html. Celle-ci comporte une table et un formulaire gere par angularJS.
La table listant les articles de presse presents dans la BD est alimentee par angularJS apres une requete au service
REST.
Lorsque l'on soumet le formulaire, un requete POST est envoyee au service REST pour creer un article dans la BD.
