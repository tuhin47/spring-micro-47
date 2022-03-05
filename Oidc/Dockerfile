FROM amazoncorretto:11-alpine-jdk
#FROM openjdk:8-jdk-alpine
COPY target/oidc-0.0.1-SNAPSHOT.war oidc-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/oidc-0.0.1-SNAPSHOT.war"]