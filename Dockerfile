FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY . .

RUN ./gradlew build

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "./build/libs/user-0.0.1-SNAPSHOT.jar"]
