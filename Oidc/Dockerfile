FROM openjdk:11
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} oidc.war
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:18765","-Djava.security.egd=file:/dev/./urandom","-jar","/oidc.war"]
EXPOSE 8765