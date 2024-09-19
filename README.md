# IN306 - Verteilte Systeme (Blog-Projekt)

## Was macht diese Anwendung?

Diese Blog-Anwendung ist ein Backend-System, das mit Quarkus entwickelt wurde und eine einfache REST-API zum Verwalten von Blogposts bereitstellt. Die Anwendung ermöglicht das Erstellen, Aktualisieren, Löschen und Abrufen von Benutzern, Blogs und Kommentaren.
Wichtig zu wissen ist das ein Blog benötigt einen User. und ein Comment benötigt einen Blog und einen User
Das System verwendet JPA (Java Persistence API) für die Datenbankinteraktion und MicroProfile OpenAPI für die Dokumentation der REST-APIs.

## Projektstruktur

Das Projekt folgt einer standardmäßigen Quarkus-Struktur:

```
├── java
│   └── ch
│       └── hftm
│           └── blog
│               ├── boundry
│               │   ├── BlogResource.java
│               │   ├── CommentResource.java
│               │   └── UserResource.java
│               ├── control
│               │   ├── BlogService.java
│               │   ├── CommentService.java
│               │   └── UserService.java
│               ├── entity
│               │   ├── Blog.java
│               │   ├── Comment.java
│               │   └── User.java
│               ├── repository
│               │   ├── BlogRepository.java
│               │   ├── CommentRepository.java
│               │   └── UserRepository.java
│               └── exception
│                   └── ObjectNotFoundException.java
└── resources
    └── application.properties
```

- **boundary**: Enthält REST-Ressourcenklassen, die HTTP-Anfragen bearbeiten.
- **control**: Enthält Service-Klassen mit der Geschäftslogik für Blogs, Kommentare und Benutzeroperationen.
- **repository**: Enthält Repository-Klassen für Datenbankoperationen.
- **entity**: Enthält Entitätsklassen, die verschiedene Komponenten der Anwendung repräsentieren.
- **exception**: Enthält benutzerdefinierte Ausnahme-Klassen zur Fehlerbehandlung.
- **application.properties**: Konfigurationsdatei für die Datenbank und andere Einstellungen.

## Entitätsklassen

- **Blog**: Repräsentiert einen Blogpost in der Anwendung. Jeder Blogpost enthält Attribute wie Titel, Inhalt, Autor und Veröffentlichungsdatum.
- **User**: Repräsentiert einen Benutzer der Blog-Anwendung. Benutzer haben Attribute wie Benutzername, E-Mail und Passwort und können Aktionen wie das Erstellen von Blogposts und Kommentaren ausführen.
- **Comment**: Repräsentiert einen Kommentar zu einem Blogpost. Jeder Kommentar enthält Attribute wie den Inhalt des Kommentars, den Benutzer, der ihn gepostet hat, und den Zeitstempel. Kommentare ermöglichen es Benutzern, an Diskussionen teilzunehmen und Feedback zu Blogposts zu geben.

## Wie startet man das Projekt?

### Voraussetzungen

- JDK 11 oder höher installiert.
- Maven installiert.

### Schritte zum Starten des Projekts

1. **Repository klonen**:
    ```sh
    git clone git@github.com:HafnerStefan/Verteilte-Systeme-Hafner.git
    ```

2. **Datenbank konfigurieren**:
    - TODO: Anleitung zur Konfiguration der Datenbank hinzufügen.

3. **Anwendung starten**:
    - Verwenden Sie Maven, um die Anwendung zu starten:
    ```sh
    ./mvnw quarkus:dev
    ```
    - Die Anwendung wird unter `http://localhost:8080` verfügbar sein.

## API-Endpunkte

http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui

## Testen

Das Projekt verwendet JUnit 5 für Unit- und Integrationstests. Um die Tests auszuführen, verwenden Sie den folgenden Befehl:

```sh
mvn test
```

# Anwendungsbereitstellung mit Docker

## Docker Container erstellen

### Docker Network erstellen
```sh
docker network create blog-nw
```

### MySQL Docker Container erstellen
```sh
docker run --name blog-mysql -p 3306:3306 --network blog-nw -e MYSQL_ROOT_PASSWORD=vs4tw -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser -e MYSQL_DATABASE=blogdb -d mysql:8.0
```

```sh
docker run --name blog-backend --network blog-nw `
  -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:mysql://blog-mysql:3306/blogdb `
  -e QUARKUS_DATASOURCE_USERNAME=dbuser `
  -e QUARKUS_DATASOURCE_PASSWORD=dbuser `
  -p 8080:8080 -d stefanhafner/quarkus/hftm:1.0.0-SNAPSHOT
```

## DB Beispiel Daten Laden

```sh
type src\main\resources\import.sql | docker exec -i blog-mysql mysql -u dbuser -pdbuser blogdb
```


## Restart des Containers

```sh
docker start blog-mysql
docker start blog-backend
```

# Docker Compose starten
## Docker Compose starten
```sh
docker-compose -f src/main/docker/docker-compose.yml up -d
```
## Docker Compose starten with example data
```sh 
docker-compose -f src/main/docker/docker-compose-with-example-sql-data.yml up -d
```


## Change History

- **Projekt erstellt**: First Quarkus Step
- **Blog-Modell und DB-Verbindung**: Quick-Start_DB-Anbindung mit Quarkus Panache
- **Repository und Service und erster Test**: Erster DB Zugriff über die Test
- **Rest Servie aufgebaut und getestet**: add Rest API
- **REST-API-Pfade hinzugefügt**: Rest und Swagger Funktioniert und ist fast perfekt

## Autor

Erstellt von Hafner Stefan.

---

Dieses README bietet einen umfassenden Überblick über die Blog-Anwendung, einschließlich ihrer Funktionalität, Projektstruktur, Entitätsklassen und Anweisungen zum Einrichten und Ausführen der Anwendung.