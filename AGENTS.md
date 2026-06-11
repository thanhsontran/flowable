# AGENTS.md

## Scope

These instructions apply to the whole repository.

## Stack

- Spring Boot 4.x application in Java 17
- Flowable 8 for BPMN workflow execution
- H2 in-memory database for local development
- Spring Data JPA with entity classes under `src/main/java/com/example/flowable/entities`
- Maven wrapper is present; prefer it over a globally installed Maven

## Preferred Commands

- Windows build: `./mvnw.cmd clean package`
- Windows test: `./mvnw.cmd test`
- Windows run: `./mvnw.cmd spring-boot:run`
- Cross-platform equivalents: `./mvnw clean package`, `./mvnw test`, `./mvnw spring-boot:run`

## Project Layout

- Application entry point: [src/main/java/com/example/flowable/FlowableApplication.java](src/main/java/com/example/flowable/FlowableApplication.java)
- REST controller layer: [src/main/java/com/example/flowable/controllers/PRController.java](src/main/java/com/example/flowable/controllers/PRController.java)
- JPA entities: [src/main/java/com/example/flowable/entities](src/main/java/com/example/flowable/entities)
- Runtime configuration: [src/main/resources/application.properties](src/main/resources/application.properties)
- SQL schema: [src/main/resources/scripts/schema.sql](src/main/resources/scripts/schema.sql)
- Flowable BPMN process: [src/main/resources/processes/pr.bpmn20.xml](src/main/resources/processes/pr.bpmn20.xml)
- Basic Spring Boot test: [src/test/java/com/example/flowable/FlowableApplicationTests.java](src/test/java/com/example/flowable/FlowableApplicationTests.java)

## Working Conventions

- Keep changes small and aligned with the current package layout under `com.example.flowable`.
- Prefer adding a service layer between controllers and persistence instead of expanding controller logic directly.
- Preserve UUID-based identifiers in entities and related persistence code.
- Match JPA mappings to the SQL schema before introducing new relationships or constraints.
- Use explicit getters and setters unless the surrounding code already adopts Lombok for that class.

## Known Pitfalls

- `spring.sql.init.mode=always` re-runs SQL initialization on each startup. Treat startup data as ephemeral unless you intentionally change that behavior.
- The schema file currently contains database-specific details that should be checked before relying on startup execution. In particular, validate column types and foreign-key ordering in [src/main/resources/scripts/schema.sql](src/main/resources/scripts/schema.sql).
- The Flowable process definition is minimal and currently routes on the `action` variable; changes to controller inputs should stay consistent with [src/main/resources/processes/pr.bpmn20.xml](src/main/resources/processes/pr.bpmn20.xml).
- Tests are currently minimal, so new behavior should usually be accompanied by focused tests rather than relying on existing coverage.

## Agent Guidance

- Before editing generated persistence or workflow code, read the nearest existing class first and stay local to that slice.
- After changing Java code, prefer validating with a narrow Maven compile or test command for the touched area.
- If startup fails, inspect configuration, SQL initialization, and BPMN resources before assuming controller or service code is at fault.