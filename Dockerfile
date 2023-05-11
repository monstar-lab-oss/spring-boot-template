FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /build
COPY . .
ARG language
RUN mvn -q clean package -Dlanguage=$language

FROM openjdk:17

WORKDIR /app
COPY --from=build /build/target/*.jar SB_template.jar
ENTRYPOINT ["java","-jar","SB_template.jar"]


