# Test Task Systemeio

This repository contains a Spring Boot application for a test task. The application provides functionality related to pricing, purchasing, and payment processing.

## Setup

1. Ensure you have Docker installed on your machine.

2. Clone this repository:
   ```zsh
   git clone https://github.com/Fuatik/systemeio
3. Navigate to the project directory:
    ```zsh
   cd <directory_name>
4. Build the application with Maven:
    ```zsh
   mvn clean package
5. Build the Docker containers:
    ```zsh
   docker-compose build
6. Start the Docker containers:
    ```zsh
   docker-compose up
7. The application should now be running. You can access the Swagger UI at http://localhost:8337/ to interact with the API endpoints.
