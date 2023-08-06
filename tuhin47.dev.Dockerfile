FROM maven:3.8.3-eclipse-temurin-17

RUN apt-get update
# RUN apt-get -y upgrade
RUN apt-get install -y inotify-tools dos2unix
RUN mkdir -p /script
COPY ./run.sh /script
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME