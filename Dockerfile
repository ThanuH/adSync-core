# Use a base image with Java 17
#FROM adoptopenjdk/openjdk17:alpine-jre
#VOLUME /tmp
#ADD target/*.jar app.jar

#create the docker file for the spring boot application
# Path: Dockerfile
# Use a base image with Java 17
FROM openjdk:17-alpine
VOLUME /tmp
ADD target/*.jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080