# spring-boot-template
Provides Sample of Project for Spring Boot and Java/Kotlin with maven setup.
The project can serve as a template for starting a project in Spring Boot with 4 options:
- Java - uses Java with the classic spring Boot recommended architecture
- Java Hexagonal - uses Java with Hexagonal architecture
- Kotlin - uses Kotlin with the classic spring Boot recommended architecture
- Kotlin Hexagonal - uses Kotlin with Hexagonal architecture

## Requirements
* JDK 17
* Any IDE with [lombok](https://projectlombok.org/) plugin installed

## How to code
Import as a java project in an IDE of your choice.

## Working locally
### How to run the app (variant 1, local with mvn commands, local Postgres)
Install and configure Postgres locally. For mac follow the [link](https://www.sqlshack.com/setting-up-a-postgresql-database-on-mac/).

Install locally a Postgres client (eg [PgAdmin](https://www.pgadmin.org/download/pgadmin-4-macos/) )

Depending on which of the 4 options you choose to follow:
Run `mvn clean install -Dlanguage=kotlinHexagonal` (kotlin/java/javaHexagonal). This will generate the appropriate jar, that will be run later on.

Before running the app set the following env variables (take advantage of the IDEA run configuration)

`ACTIVE_PROFILE=` dev / qa / prod

`DATABASE_HOST=`

`DATABASE_PORT=`

`DATABASE_NAME=`

`DATABASE_USER=`

`DATABASE_PASSWORD=`

Run `mvn spring-boot:run -f pom.xml`

### How to run the app (variant 2, docker)
Create directory to store db files `/var/lib/postgresql/data/`

Depending on which of the 4 options (language/architecture) you choose to follow:
`docker build --build-arg language=kotlinHexagonal -t template_app .` (`kotlinHexagonal` can be replaced with `kotlin`, `java` or `javaHexagonal`)

Edit database connection properties in `run.sh`

Run `sh run.sh`

## Run tests
`mvn test`
