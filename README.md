# spring-boot-task-management-api
Spring Boot Task Management API with CRUD, H2, and Swagger.

## Rule Engine Integration
This API communicates with the standalone **Config-Driven Rule Engine microservice** to validate task descriptions before creation. The task service sends the input text to the rule engine, and if it matches any predefined rule (e.g., BLOCK or PRIORITY), the creation is rejected. This microservice-based approach keeps business rules decoupled and allows rule logic to evolve without changing the main API codebase.

<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/355a4070-ea11-47fc-9cf4-78ee8413f80e" />
