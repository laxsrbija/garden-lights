FROM gradle:8 AS build

WORKDIR /tmp
COPY . .

RUN gradle buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /opt/garden-lights
COPY --from=build /tmp/build/libs/garden-lights.jar .
COPY src/main/resources/application.yaml resources/application.yaml

CMD java -jar garden-lights.jar -config="resources/application.yaml"