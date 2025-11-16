FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/AstonExternalConfiguration-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]