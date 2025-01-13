FROM maven:3.9-eclipse-temurin-22 as MAVEN_BUILD

RUN mkdir -p /app
WORKDIR /app

COPY pom.xml ./
COPY apigateway/pom.xml ./apigateway/
COPY authservice/pom.xml ./authservice/
COPY aws-reactive/pom.xml ./aws-reactive/
COPY chat-service/pom.xml ./chat-service/
COPY common/pom.xml ./common/
COPY configserver/pom.xml ./configserver/
COPY orderservice/pom.xml ./orderservice/
COPY paymentservice/pom.xml ./paymentservice/
COPY productservice/pom.xml ./productservice/
COPY saga/pom.xml ./saga/
COPY service-registry/pom.xml ./service-registry/

RUN mvn dependency:go-offline -B


COPY . .
RUN mvn clean package -DskipTests
