FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/astonGateway-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]