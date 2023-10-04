# Spring Boot + React starter

Ce projet propose un exemple d'application basée sur le framework Spring Boot et mettant en oeuvre des composants React.

## Pré-requis

* [Java](https://openjdk.org/projects/jdk/17/) version 17.x
  * pour les utilisateurs de [`jenv`](https://www.jenv.be/), la version de Java est fixée dans le fichier `.java-version`
* [Node](https://nodejs.org/fr/download) version 18.x
  * pour les utilisateurs de [`nvm`](https://github.com/nvm-sh/nvm), la version de Node est fixée dans le fichier `.nvmrc`

## La partie React

Le code des composants React se trouve dans le dossier `src/main/js`.

Les composants sont construits avec Webpack. La configuration se trouve dans le fichier `webpack.config.js` à la racine du projet.

Afin de lancer le build de la partie React indépendamment, lancez la commande `yarn watch`, après avoir lancé un `yarn install` si nécessaire.

## La partie CSS

Un préprocesseur SCSS est configuré dans le projet. Le code SCSS se trouve dans le dossier `src/main/scss`.

Comme pour les composants React, les fichier CSS minifiés sont construits avec Webpack. La commande `yarn watch` permet donc de lancer également le build de la partie SCSS.

### Thème bootstrap

Le code SCSS de Bootstrap se trouve également dans le dossier `src/main/scss`. Le CSS minifié de Bootstrap est donc construit à partir des sources SCSS.
Cela permet de définir un thème personnalisé pour Serli, en redéfinissant certaines variables dans le fichier `src/main/scss/bootstrap/_variables.scss`, notamment les "primary & secondary colors": 

```scss
// scss-docs-start theme-color-variables
$primary:       #1768b1 !default;
$secondary:     #3db8d8 !default;
```

## Pour démarrer l'application

Lancez la commande : `./mvnw spring-boot:run`

Puis rendez-vous sur l'URL : [http://localhost:8080](http://localhost:8080)

### Une précision importante

Le `yarn watch` n'est nécessaire que si les développements réalisés concernent la partie front de l'application (React ou SCSS).
Sinon, la partie front est construite automatiquement lors du lancement avec `./mvnw spring-boot:run` grâce au plugin `frontend-maven-plugin` (Voir la configuration dans le `pom.xml` pour tous les détails).
