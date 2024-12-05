# Taco Cloud

Taco Cloud is a web application for managing taco orders. It includes an admin server, an authorization server, and a main application server. The project is built using Java, Spring Boot, and MongoDB, and it leverages reactive programming for handling asynchronous data streams.

## Project Structure

### Admin Server

The admin server is responsible for monitoring the application's health and metrics. It uses Spring Boot Actuator to provide various endpoints for checking the application's status.

### Authorization Server

The authorization server handles authentication and authorization for accessing the API. It uses JWT (JSON Web Token) to secure the endpoints and manage user access.

### Main Application Server

The main application server is the core of the Taco Cloud application. It handles the business logic for creating and managing taco orders. It interacts with MongoDB to store and retrieve data and uses reactive programming to handle data streams efficiently.

## Technology Stack

- **Java**: The primary programming language used for the application.
- **Spring Boot**: A framework that simplifies the development of Java applications.
- **Spring Security**: Used for securing the application and managing user authentication and authorization.
- **Spring Data MongoDB**: Provides integration with MongoDB for data storage.
- **Spring WebFlux**: Supports reactive programming for handling asynchronous data streams.
- **JWT (JSON Web Token)**: Used for securing the application endpoints.
- **Spring Boot Actuator**: Provides production-ready features to help you monitor and manage your application.
- **Maven**: A build automation tool used for managing project dependencies and building the application.
