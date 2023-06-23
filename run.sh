#!/bin/bash
#dos2unix mvnw
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEUBG_PORT" &
while true; do
  inotifywait -e modify,create,delete,move -r ./src/ && mvn compile
done