# Microservice 101 Example Service

This project is a study of how a basic API microservice should be

## Used libraries
* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-boot-starter-validation
* spring-boot-starter-test
* springfox-swagger-ui 
* mapstruct
* h2
* Lombok
* rest-assured


## Follow the below steps to containerize the application

```shell
$ mvn clean package

$ java -jar target/docker-message-server-1.0.0.jar

$ docker build --tag=advertisement-server:latest .

$ docker run -p8080 --name advertisement-server advertisement-server:latest
```