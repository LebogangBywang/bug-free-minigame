FROM ubuntu:21.04

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl

ADD output/robot-worlds-server-1.0.1-jar-with-dependencies.jar /srv/server-1.0.1.jar

WORKDIR /srv
EXPOSE 5000
CMD ["java", "-jar", "server-1.0.1.jar", "-p", "5000", "-s", "2"]