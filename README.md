# User Service
## Requirement
- JDK 8+
- MySQL
- Gradle Plugin 5.6.x or later
- Docker (Optional)

## Installation
### Running with docker compose
The easy way to run the application locally is by using `Compose`. To run the application type `docker-compose up` from the terminal located in the application folder. This will build (if needed) the Docker images and bring up all the containers.

**Note**: Only Linux containers are supported currently.

### Running with manual
1. Start and create a database
2. Change database configuration at `./src/main/resources/application.yml` with an example config below
```yaml
...
spring:
  datasource:
    url: jdbc:mysql://<your_database_host>:<your_database_port>/<your_database_name>?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
    username: <your_database_username>
    password: <your_database_password>
```
3. Then you can run the command below to start the application
```
./gradlew --refresh-dependencies
./gradlew bootRun
```
or
```
./gradlew --refresh-dependencies
./gradlew build
java -jar ./build/libs/user
```

### Start
After you start the application you can go to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to see the API Documentation
