FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8080
WORKDIR /app
ARG JAR=hello-0.0.1-SNAPSHOT.jar

COPY  /target/$JAR /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]