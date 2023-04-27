# spring-boot-template
Provides Sample of Project for Spring Boot and Java/Kotlin with maven setup

## Requirements
* JDK 17
* Any IDE with [lombok](https://projectlombok.org/) plugin installed

## How to code
Import as a java project in an IDE of your choice.

### Code style
Project uses [Google Java](https://google.github.io/styleguide/javaguide.html) code convention.

`./mvnw validate` - performs code style analysis

## App monitor
* `curl http://localhost:8080/actuator` - returns list of available monitor related endpoints
* `curl http://localhost:8080/actuator/health` - returns app health status

## Working locally
### Database (variant 1, local Postgres)
Install and configure Postgres locally. For mac follow the [link](https://www.sqlshack.com/setting-up-a-postgresql-database-on-mac/).

Install locally a Postgres client (eg [PgAdmin](https://www.pgadmin.org/download/pgadmin-4-macos/) )

### Database (variant 2, docker Postgres)

Create directory to store db files `/var/lib/postgresql/data/`

Navigate to repository `./database` directory

Edit database connection properties in `run_database.sh`

Run `run_database.sh`

## How to run the app
Before running the app set the following env variables

`ACTIVE_PROFILE=` dev / qa / prod

`DATABASE_HOST=`

`DATABASE_PORT=`

`DATABASE_NAME=`

`DATABASE_USER=`

`DATABASE_PASSWORD=`

Run `./mvnw spring-boot:run`


## Run tests
`./mvnw test`
