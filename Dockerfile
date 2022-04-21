FROM openjdk:11-jdk-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
COPY frontend ./frontend
CMD ["./mvnw", "spring-boot:run"]
