# Spring Boot + React starter

Ce projet propose un exemple d'application mettant en oeuvre : 
* un backend avec le framework Spring Boot,
* un exemple CRUD accédant à des données dans une base PostgreSQL,
* des composants React,
* un thème Bootstrap personnalisé aux couleurs de Serli.

L'objectif principal de ce starter est de mettre en oeuvre la "tuyauterie" permettant de construire l'application en une seule commande de build, qui gère à la fois la partie back (build Maven de l'app Spring Boot) et la partie front (build Webpack pour la partie Javascript et SCSS).

## Pré-requis

Le projet nécessite les pré-requis suivants :

* [Java](https://openjdk.org/projects/jdk/17/) version 17.x
  * pour les utilisateurs de [`jenv`](https://www.jenv.be/), la version de Java est fixée dans le fichier `.java-version`
* [Node](https://nodejs.org/fr/download) version 18.x
  * pour les utilisateurs de [`nvm`](https://github.com/nvm-sh/nvm), la version de Node est fixée dans le fichier `.nvmrc`
* [Yarn](https://yarnpkg.com/) version 1.22.x
* [PostgreSQL](https://www.postgresql.org/) version 15.x
  * l'application démarre quand même sans base de données, mais une installation locale de PostgreSQL est nécessaire pour faire fonctionner la partie CRUD. 

## Architecure du code

En plus des fichiers et dossiers classiques d'une application Spring Boot, vous trouverez : 
* Les composants React dans le dossier `src/main/js`
* Les fichiers SCSS dans le dossier `src/main/scss`
* Un fichier `package.json` et un fichier `webpack.config.js`, à la racine du projet, permettant de gérer le build de la partie front (React + SCSS).

## Pour démarrer l'application

Lancez la commande `./mvnw spring-boot:run` puis rendez-vous sur l'URL [http://localhost:8080](http://localhost:8080).

La partie front est construite automatiquement lors du lancement de l'application grâce au plugin `frontend-maven-plugin`.

Les commandes exécutées par le `frontend-maven-plugin` sont les suivantes (voir la configuration dans le `pom.xml` pour tous les détails) : 
1. Installation de Node et Yarn
2. Installation des dépendances : `yarn install`
3. Nettoyage des builds précédents : `yarn clean`
4. Build Webpack : `webpack`

## Le build du Front

### La partie React

Le code des composants React se trouve dans le dossier `src/main/js`.

Les composants sont construits avec Webpack. La configuration se trouve dans le fichier `webpack.config.js` à la racine du projet.

Afin de lancer le build de la partie React indépendamment du run Spring Boot, lancez la commande `yarn watch` (après avoir lancé un `yarn install` si nécessaire).

#### Exemple du composant `HelloWorldWidget`

Un point d'entrée est défini dans la configuration Webpack pour le composant `HelloWorldWidget` : 

```javascript
module.exports = {
  entry: {
    HelloWorldWidget: './src/main/js/HelloWorldWidget.js'
  }
  // ...
}
```

Le composant exporte une méthode `mount(<props>)` permettant de le monter sur un noeud du DOM : 

```javascript
export function mount(props) {
    const container = document.getElementById(props.containerId)
    const root = createRoot(container);
    return root.render(<HelloWorld {...props} />);
}
```

Une fois construit par Webpack, le composant peut être importé dans une page HTML et monté dans un noeud spécifique du DOM :  

```html
<body>
<main layout:fragment="content">
    <h1>Exemple de composant React</h1>
    <p>Voici un exemple de composant React monté dans la page HTML :</p>
    <div id="hello-world-container"></div>
</main>
<th:block layout:fragment="moreScripts">
    <script type="text/javascript" th:inline="javascript">
        function mountWidget() {
            HelloWorldWidget.mount({
                containerId: 'hello-world-container',
                name: [[${name}]]
            })
        }
    </script>
    <script src="/dist/js/HelloWorldWidget.js" onload="mountWidget()"></script>
</th:block>
</body>
```

À noter : 
* Afin de pouvoir utiliser des variables du `Model` Spring Boot dans la partie `<script>` du template Thymleaf, il ne faut pas oublier l'attribut `th:inline="javascript"`
* La syntaxe pour utiliser les variables dans la partie `<script>` est un peu particulière. Par exemple ici : `[[${name}]]`. Voici un article plutôt bien fait qui détaille les éléments de syntaxe : [https://attacomsian.com/blog/thymeleaf-set-javascript-variable](https://attacomsian.com/blog/thymeleaf-set-javascript-variable)

### La partie CSS

Un préprocesseur SCSS est configuré dans le projet. Le code SCSS se trouve dans le dossier `src/main/scss`.

Comme pour les composants React, les fichiers CSS minifiés sont construits avec Webpack. La commande `yarn watch` permet donc de lancer également le build de la partie SCSS.

#### Thème bootstrap

Le code SCSS de Bootstrap se trouve également dans le dossier `src/main/scss`. Le CSS minifié de Bootstrap est donc construit à partir des sources SCSS.
Cela permet de définir un thème personnalisé pour Serli, en redéfinissant certaines variables dans le fichier `src/main/scss/bootstrap/_variables.scss`, notamment les "primary & secondary colors": 

```scss
// scss-docs-start theme-color-variables
$primary:       #1768b1 !default;
$secondary:     #3db8d8 !default;
```

### Optimisations

#### PurgeCSSPlugin

Le plugin Webpack `PurgeCSSPlugin` permet de conserver uniquement le CSS réellement utilisé dans l'application. Il peut être ajouté dans la configuration Webpack : 

```javascript
    plugins: [
        // ...
        new PurgeCSSPlugin({
            paths: () => glob.sync(['./src/main/resources/templates/**/*.html', './src/main/js/**/*.js']),
            safelist: []
        })
    ]
```

Attention, cela peut poser problème quand des classes CSS sont ajoutées dynamiquement en Javascript. 
Pour résoudre ce soucis, il est possible de définir une `safelist`. Par exemple, pour conserver les classes ajoutées par la librairie [Leaflet](https://leafletjs.com/), on peut définir la `safelist` suivante : `safelist: [/^leaflet-*/]`

#### Fontawesome

Actuellement, l'ensemble des icones Fontawesome sont chargées : 

```html
<link rel="stylesheet" href="/fontawesome/css/all.min.css">
```

Or il est possible de ne charger que les catégories d'icones utilisées réellement dans l'application. Par exemple `solid` et `brands` : 

```html
<link rel="stylesheet" href="/fontawesome/css/fontawesome.min.css">
<link rel="stylesheet" href="/fontawesome/css/solid.min.css">
<link rel="stylesheet" href="/fontawesome/css/brands.min.css">
```

Pour en savoir plus : [https://fontawesome.com/docs/web/setup/host-yourself/webfonts](https://fontawesome.com/docs/web/setup/host-yourself/webfonts#reference-font-awesome-in-your-project)

## Base de données

### Création de la base

Pour faire fonctionner la partie CRUD de l'application, vous devez créer une base locale avec la configuration suivante : 

```sql
CREATE DATABASE todos_db;
CREATE USER todos_db_user SUPERUSER ENCRYPTED PASSWORD 'todos_db_password';
```

Ensuite, il est nécessaire de jouer le script SQL suivant afin de créer les structures de données nécessaires (types, tables, fonctions et triggers) : `src/main/resources/database/todos-up.sql`.

Si vous souhaitez ré-initialiser la base de données, il est possible de jouer le script SQL `src/main/resources/database/todos-down.sql`.

La partie CRUD de l'application est disponible à cette URL : [http://localhost:8080/todos](http://localhost:8080/todos)
