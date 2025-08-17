# 🔖BookNest Backend

A RESTful API for a book management application. The goal of this project is to provide a backend for users to register, log in, and manage their personal book collections.

## Features

This project currently implements the core user foundation, which includes a robust and secure registration system.

* **User Registration:** A `/api/auth/register` endpoint that allows new users to create an account.

* **Data Validation:** All incoming registration data is automatically validated to ensure required fields are present and correctly formatted (e.g., a valid email address).

* **Secure Password Handling:** User passwords are encrypted before being stored in the database, ensuring a high level of security.

* **Layered Architecture:** The project is structured with dedicated layers for controllers, services, and repositories, promoting clean code and scalability.

## Technology Stack

The project is built using modern Java and the Spring Boot framework.

* **Java:** The primary programming language.

* **Spring Boot:** The framework used to create the stand-alone, production-grade application.

* **Spring Data JPA:** Used for easy and efficient database interaction.

* **Maven:** The build automation and dependency management tool.

* **Lombok:** A library that reduces boilerplate code by automatically generating getters, setters, and constructors.

## API Endpoints

The following endpoints are currently available. All requests and responses are in JSON format.

### 1. Register a new user

* **URL:** `/api/auth/register`

* **Method:** `POST`

* **Request Body:**
 {
"username": "your_username",
"email": "your_email@example.com",
"password": "your_secure_password"
}
* **Success Response:** `HTTP 200 OK` with the message "User registered successfully!"

* **Error Response:** `HTTP 400 Bad Request` if the email is already in use or if validation fails.

## Getting Started

Follow these instructions to get a local copy of the project up and running.

### Prerequisites

* Java 17 or higher

* Maven

* A relational database (e.g., PostgreSQL, MySQL)

## Future Enhancements

* JWT-based user authentication and login functionality.

* User profiles.

* Ability to create, edit, and delete private and public book lists.

* Search functionality for books.

## License
This project is licensed under the MIT License.
