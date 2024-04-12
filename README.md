# Social Media App Documentation

## Overview

The Social Media App is a simple RESTful API application that allows users to create posts, like posts, and follow other users. The application is built using Spring Boot and uses PostgresSQL database for persistence.

## Database Configuration

The application uses Spring Data JPA to interact with the database. The database configuration is specified in the `application.properties` file:
```properties
# Postgres
spring.datasource.url=${POSTGRES_HOST_URL}/social_media_db
spring.datasource.username=${POSTGRES_USERNAME}
spring.datasource.password=${POSTGRES_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

## Running the Application
1. Clone the repository:
```bash
git clone https://github.com/eshonkulov-asliddin/socia-media-app.git
```
2. Navigate into the project directory:
```bash
cd socia-media-app  
```
3. Run the application:
```bash
./gradlew bootRun
```

## Running Tests and Generating Code Coverage Report
You can run the tests and generate the code coverage report with the following command:
```bash
./gradlew test jacocoTestReport
```
The code coverage report is saved in the build/reports/jacoco/test directory.

## Running SonarQube Analysis (Optional)

If you have SonarQube installed, you can run the SonarQube analysis with the following command:
```bash
./gradlew sonarqube
```
