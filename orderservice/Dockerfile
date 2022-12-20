FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} orderservice.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:18082","-Djava.security.egd=file:/dev/./urandom","-jar", "/orderservice.jar"]

EXPOSE 8082