# syntax=docker/dockerfile:1

#FROM openjdk:17-oracle
#FROM openjdk:19-jdk-alpine
FROM openjdk:20
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]