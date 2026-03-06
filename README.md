# Nutri Ledger API

A RESTful API for tracking daily food intake and calorie consumption. This application helps users maintain a food diary by logging meals and monitoring their daily caloric intake.

## 📋 About

Nutri Ledger API is a backend service that provides comprehensive functionality for managing daily food diaries. Users can track their meals, calculate calorie consumption, and monitor their nutritional intake over time. The application is built with a clean architecture approach, ensuring maintainability and scalability.

## 🛠️ Tech Stack

### Core Framework
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 4.0.3** - Application framework and dependency injection
- **Maven** - Dependency management and build tool

### Main Dependencies
- **Spring Web MVC** - RESTful API development
- **Spring Data JPA** - Database access and ORM
- **Lombok** - Reduces boilerplate code with annotations
- **Spring Boot DevTools** - Hot reload and development utilities

### Architecture
The project follows a layered architecture pattern:
- **Domain Layer** - Core business logic and entities
- **Application Layer** - Use cases and business orchestration
- **Infrastructure Layer** - Database, repositories, and external integrations
- **Entrypoint Layer** - REST controllers and API endpoints

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+

### Running the Application

```bash
# Clone the repository
git clone <repository-url>

# Navigate to the project directory
cd nutri-ledger-api

# Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📁 Project Structure

```
src/main/java/io/github/pratesjr/nutriledgerapi/
├── application/         # Application layer
│   └── usecases/       # Business use cases
├── domain/             # Domain models and business logic
├── entrypoint/         # REST controllers and API endpoints
└── infra/              # Infrastructure layer
    ├── config/         # Configuration classes
    ├── database/       # Database configurations
    ├── entities/       # JPA entities
    ├── persistence/    # Persistence adapters
    └── repositories/   # Spring Data repositories
```

## 🔧 Configuration

Application configuration can be found in `src/main/resources/application.yaml`

## 📚 API Documentation

(Coming soon - API documentation will be added)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the terms specified in the project configuration.

