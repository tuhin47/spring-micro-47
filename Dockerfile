#FROM maven:3.8.3-eclipse-temurin-11 as MAVEN_BUILD
#
#MAINTAINER MD Towhidul Islam <tuhintowhidul@gmail.com>
#
#WORKDIR /build
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline -B
#COPY ./src ./src
#RUN ./mvnw clean package -DskipTests

FROM openjdk:11

COPY ./target/*.jar /app/app.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]
