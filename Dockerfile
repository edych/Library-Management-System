FROM openjdk:11-jdk-slim
WORKDIR /app
ADD target/library-management-system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx268M", "-Xss512K", "-jar", "/app/app.jar"]
