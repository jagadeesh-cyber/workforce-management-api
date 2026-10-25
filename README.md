# Workforce Management API
A RESTful Employee Management application built using **Java 17** and **Spring Boot 3.5**. 
The application provides APIs to manage employee records, search employees, and retrieve employee analytics. 
It also demonstrates modern Java features, input validation, and centralized exception handling.

## Features
- Create a new employee.
- Retrieve all employees or an employee by ID.
- Update employee details.
- Delete an employee.
- Search employees by name.
- Retrieve employee analytics.
- Demonstrate Java 8 features.
- Demonstrate Java 17 features.
- Validate request data.
- Handle exceptions centrally using a global exception handler.

## Technology Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.5
- **Build Tool:** Maven
- **API Style:** REST
- **Data Storage:** In-memory storage using `ConcurrentHashMap`
- **API Testing:** Postman
- **Version Control:** Git and GitHub

## Application Architecture
The application follows a layered architecture:

- Controller Layer: Handles incoming HTTP requests and returns API responses.
- Service Layer: Contains business logic and employee-related operations.
- Model Layer: Represents employee data.
- Exception Layer: Handles application errors centrally.
- In-Memory Storage: Stores employee records using ConcurrentHashMap.

### Request Flow
Client (Postman)
↓
REST Controller
↓
Service Layer
↓
ConcurrentHashMap
↓
HTTP Response

## Project Structure
```text
workforce-management-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── [application package]/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       ├── exception/
│   │   │       └── WorkforceManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

*Note: The package and folder names above illustrate the intended organization. Keep the actual package structure from your project if it differs.*

## Prerequisites
Make sure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Maven
- IntelliJ IDEA or another Java IDE
- Postman for API testing
- Git

Verify your Java installation:
```bash
java -version
```

Verify Maven:
```bash
mvn -version
```

## Getting Started
### 1. Clone the Repository
```bash
git clone https://github.com/jagadeesh-cyber/workforce-management-api.git
cd workforce-management-api
```

### 2. Build the Application
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```
Alternatively, run the main application class from your IDE.

Once the application starts successfully, the APIs should be accessible at:
```text
http://localhost:8080
```

## REST API Endpoints
The following endpoints are provided by the application.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/employees` | Retrieve all employees |
| GET | `/api/employees/{id}` | Retrieve an employee by ID |
| POST | `/api/employees` | Create an employee |
| PUT | `/api/employees/{id}` | Update an employee |
| DELETE | `/api/employees/{id}` | Delete an employee |
| GET | `/api/employees/search?name=an` | Search employees by name |
| GET | `/api/employees/analytics` | Retrieve employee analytics |
| GET | `/api/features/java8` | Demonstrate Java 8 features |
| GET | `/api/features/java17` | Demonstrate Java 17 features |

## API Testing with Postman
Base URL:
```text
http://localhost:8080
```

### 1. Create an Employee
**Method:** `POST`
**URL:**
```text
http://localhost:8080/api/employees
```

**Headers:**
```text
Content-Type: application/json
```

**Sample request body:**
```json
{
  "name": "Rahul Sharma",
  "department": "Engineering",
  "salary": 85000,
  "active": true
}
```
Send the request and verify that the employee is created successfully. The exact response fields depend on the application's model and ID-generation logic.

### 2. Retrieve All Employees
**Method:** `GET`
```text
http://localhost:8080/api/employees
```
Returns the employee records currently held by the application.

### 3. Retrieve an Employee by ID
**Method:** `GET`
```text
http://localhost:8080/api/employees/1
```
Replace `1` with an employee ID that exists in the application.

### 4. Update an Employee
**Method:** `PUT`
```text
http://localhost:8080/api/employees/1
```

**Sample request body:**
```json
{
  "name": "Rahul Sharma",
  "department": "Technology",
  "salary": 90000,
  "active": true
}
```
Verify that the employee's details are updated.

### 5. Delete an Employee
**Method:** `DELETE`
```text
http://localhost:8080/api/employees/1
```
Replace `1` with the ID of the employee you want to delete.

### 6. Search Employees by Name
**Method:** `GET`
```text
http://localhost:8080/api/employees/search?name=rah
```
Returns employees matching the supplied name search term.

### 7. Retrieve Employee Analytics
**Method:** `GET`
```text
http://localhost:8080/api/employees/analytics
```
Use this endpoint to view the analytics calculated by the application.

### 8. Explore Java Features
Java 8 demonstration:
```text
GET http://localhost:8080/api/features/java8
```

Java 17 demonstration:
```text
GET http://localhost:8080/api/features/java17
```
These endpoints demonstrate selected language and API features supported by the respective Java versions.

## Error Handling and Validation
The application demonstrates request validation and centralized exception handling.

- Invalid request data can be rejected by validation rules.
- Errors can be handled centrally through the global exception handler.
- Requests for nonexistent employee IDs can return an appropriate error response, depending on the implemented exception mapping.

## Data Storage
This project uses an in-memory `ConcurrentHashMap` for employee storage instead of a persistent database.
**Important:** Employee records are not persisted across application restarts. The application may also initialize sample employee records when it starts.


## Future Enhancements and Roadmap
The project will be enhanced incrementally to explore distributed systems
and cloud-native backend development.

### Phase 1: Persistence and Testing
- Integrate MySQL or PostgreSQL with Spring Data JPA and Hibernate.
- Add unit and integration tests.
- Improve API validation and documentation.

### Phase 2: Microservices Architecture
- Split the application into independent business services.
- Implement synchronous communication between services using OpenFeign.
- Explore API Gateway and service discovery.
- Add resilience patterns such as retries and circuit breakers.

### Phase 3: Event-Driven Architecture with Kafka
- Integrate Apache Kafka in KRaft mode.
- Implement asynchronous communication between services.
- Explore consumer groups, partitions, offsets, and message retries.
- Implement error handling and dead-letter topics.

### Phase 4: Cloud and Deployment
- Containerize services using Docker.
- Explore AWS services for application deployment.
- Implement CI/CD and centralized logging and monitoring.

*These enhancements are planned and will be implemented incrementally.*