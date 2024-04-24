FROM maven:3.9-eclipse-temurin-17 as build

WORKDIR /tmp
COPY . .

RUN mvn -B clean package

FROM eclipse-temurin:17.0.11_9-jre

WORKDIR /opt/garden-lights
COPY --from=build /tmp/target/garden-lights-0.0.1-SNAPSHOT.jar .
COPY src/main/resources/application.yml resources/application.yml

CMD ["java", "-jar", "garden-lights-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:./resources/application.yml"]