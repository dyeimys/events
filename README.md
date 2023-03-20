# events
[![Java CI with Gradle](https://github.com/dyeimys/events/actions/workflows/gradle.yml/badge.svg)](https://github.com/dyeimys/events/actions/workflows/gradle.yml)

## :whale: Run in docker ##
```
docker pull ghcr.io/dyeimys/events-api:latest
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