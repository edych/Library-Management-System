# **<p align="center">📚 Library Management System 📚</p>**
<hr>

[![CircleCI](https://circleci.com/gh/edych/Library-Management-System/tree/main.svg?style=svg)](https://circleci.com/gh/edych/Library-Management-System/tree/main) [![codecov](https://codecov.io/gh/edych/Library-Management-System/branch/main/graph/badge.svg?token=ZDNRFFVSE4)](https://codecov.io/gh/edych/Library-Management-System)

Full stack app for library management.
Frontend side developed with Vaadin. 


<b>Technologies used</b>: Spring Boot, AWS ECS, AWS ECR, CircleCi, codecov, Jacoco, Vaadin, H2, Liquibase, Docker, Lombok, Mapstruct, Swagger, Zalando Problem.

### App overview
#### Library page
* contains list of ```Books``` stored in the database
* when no records are stored then information "No records" will be displayed 
* there is also a button to add a new record

#### New Record Page
* displays a form with fields: ```author, title, ISBN``` and button ```Add```
* on submitting the form, application persists new record to the database and redirects to Library page
* each form field is validated. If any of the fields is not valid, the button ```Add``` will be deactivated, making it impossible to submit the object to the database

<hr>

### Requirements
* Java 11

## Building and running the app

### Docker
```bash
docker build -t library-management-system .
docker run -p 8080:8080 library-management-system
```

### Maven
```bash
./mvnw clean install
java -jar library-management-system-0.0.1-SNAPSHOT.jar
```
## API

### AWS ECS
Live version is running at [http://ec2-34-241-156-46.eu-west-1.compute.amazonaws.com/ui/library](http://ec2-34-241-156-46.eu-west-1.compute.amazonaws.com/ui/library)


### Swagger UI
Documentation available at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

[http://ec2-34-241-156-46.eu-west-1.compute.amazonaws.com/swagger-ui/index.html#/](http://ec2-34-241-156-46.eu-west-1.compute.amazonaws.com/swagger-ui/index.html#/)


### H2
H2 Console is available at [http://localhost:8080/h2](http://localhost:8080/h2)

### Postman
There is `Library-Management-System.postman_collection.json` collection provided in the root of repository.

### Curls
**1. Add a new *Book***
```bash
curl --location --request POST 'localhost:8080/books/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "author": "Ane Doe",
    "title": "Title",
    "isbn": "978-3-16-148410-0"
}'
```

**2. Get all *Books***<br>
```bash
curl --location --request GET 'localhost:8080/books/' \
--data-raw ''
```