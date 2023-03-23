# events
[![Java CI with Gradle](https://github.com/dyeimys/events/actions/workflows/gradle.yml/badge.svg)](https://github.com/dyeimys/events/actions/workflows/gradle.yml)


## :arrow_forward: Installation
### Prerequisites ##
To run this project, you will need to have the following software installed on your system:

- JDK 17
- Gradle 7+
### Run local
1. Clone the repository: 
```bash
git clone https://github.com/dyeimys/events.git
```
2. Navigate to the project root directory: 
```bash
cd events
```
3. Run the project: 
```bash
./gradlew bootRun
```

4. Access the application at http://localhost:8080/swagger-ui.html
## :whale: Run in docker
```bash
docker pull ghcr.io/dyeimys/events-api:latest
```

```bash
docker run -p 8080:8080 ghcr.io/dyeimys/events-api:latest 
```
🔗 Access Swagger: [swagger-ui/index](http://localhost:8080/swagger-ui/index.html)

## :package: Packages organization
```sql
├── kotlin
│└── br
│  └── dfranco
│     └── learn
│          └── events
│              ├── application
│              │   ├── dtos
│              │   ├── mappers
│              │   ├── services
│              │   └── usecases
│              ├── domain
│              │   ├── entities
│              │   ├── enuns
│              │   └── exceptions
│              ├── infrastructure
│              │   ├── kafka
│              │   └── persistence
│              └── presentation
│                  ├── controllers
│                  ├── mappers
│                  ├── requests
│                  └── responses

```


Each layer has its own responsibilities and concerns, following the separation of concerns principle. The packages are organized as follows:

- `application`: contains the use cases, which represents the application's business rules. This layer uses DTOs to transfer data between layers and mappers to convert between DTOs and entities.
- `domain`: contains the entities, which represent the business domain concepts, and the enums and exceptions used by the domain.
- `infrastructure`: contains the implementations of the interfaces defined by the `application` and `domain` layers. In this project, it contains the persistence and Kafka implementations.
- `presentation`: contains the web controllers and their DTOs and mappers. The controllers receive the requests, use the `application` layer to perform the business logic, and return the responses to the client.

## :heavy_check_mark: Built With
- Kotlin
- Spring Boot
- JPA/Hibernate
- JUnit 5
- Mockito
