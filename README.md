# Job Portal Backend - Setup Guide

## Preparations
Before setting up the project, ensure you have the following installed on your system:
- **Java 17+**
- **Maven**
- **MySQL**
- **Postman** (optional, for testing APIs)

## 1. Clone the Repository
```bash
git clone <repository-url>
```

## 2. Configure the Database
1. Start MySQL and create a new database:
CREATE DATABASE job_portal;

2. Update `src/main/resources/application.properties` with your database credentials:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

## 3. Install Dependencies
Run the following command to install dependencies:
```bash
mvn clean install
```

## 4. Run the Application
Start the Spring Boot application using:
```bash
mvn spring-boot:run
```
## 5. API Testing
1. Import the provided Postman collection (`postman_collection.json`) into Postman.
2. Use the predefined endpoints to test various functionalities.

## 6. Stopping the Application
- If running with Maven:
```bash
CTRL + C
```

## 7. Common Issues & Fixes
- **Database Connection Failure:** Ensure MySQL is running and credentials are correct.
- **Port Conflicts:** Change the port in `application.properties` if needed:
```properties
server.port=8081
```
**Your backend is now ready to use! 🚀**

