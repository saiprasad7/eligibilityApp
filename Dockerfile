FROM openjdk:8-jdk-alpine

ENV SPRING_PROFILES_ACTIVE docker

EXPOSE 8080

RUN mkdir -p /build

WORKDIR /build

ENV JAR_FILE=*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
