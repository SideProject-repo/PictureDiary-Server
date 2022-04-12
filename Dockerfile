FROM openjdk:11.0.10-jre-slim-buster

ARG JAR_FILE=build/libs/picture-diary-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ARG PROPERTIES_FILE=application.yml
COPY ${PROPERTIES_FILE} application.yml

ENTRYPOINT ["java", "-jar", "/app.jar", "-Dspring.config.location=/application.yml"]
