# syntax=docker/dockerfile:1

FROM --platform=linux/amd64 eclipse-temurin:17-jdk-jammy
VOLUME /tmp
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]