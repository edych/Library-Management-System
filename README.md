# **<p align="center">📚 Library Management System 📚</p>**
<hr>

[![CircleCI](https://circleci.com/gh/edych/Library-Management-System/tree/main.svg?style=svg)](https://circleci.com/gh/edych/Library-Management-System/tree/main) [![codecov](https://codecov.io/gh/edych/Library-Management-System/branch/main/graph/badge.svg?token=ZDNRFFVSE4)](https://codecov.io/gh/edych/Library-Management-System)


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