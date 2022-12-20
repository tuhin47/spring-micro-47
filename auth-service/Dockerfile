FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} authservice.jar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:17777","-Djava.security.egd=file:/dev/./urandom","-jar","/authservice.jar"]

EXPOSE 7777