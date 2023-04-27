FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /build
COPY . .
#RUN mvn -q clean package -Dlanguage=$language
#RUN mvn -q clean package -Dlanguage=kotlin
RUN mvn -q clean package -Dlanguage=java

FROM openjdk:17

WORKDIR /app
COPY --from=build /build/target/*.jar SB_template_java.jar
CMD ["java", "-jar", "SB_template_java.jar"]
#COPY --from=build /build/target/*.jar SB_template_$language.jar
#CMD ["java", "-jar", "SB_template_$language.jar"]
#COPY --from=build /build/target/*.jar SB_template_kotlin.jar
#CMD ["java", "-jar", "SB_template_kotlin.jar"]
