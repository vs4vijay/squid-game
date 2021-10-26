# Squid Game

---

## Tech. Stack

- Java
- Spring 5 with Spring Boot 2
- Hibernate / JPA
- H2 Database
- Lombok
- MapStruct
- Axon Framework

---

## Running

- `gradlew bootRun`

---

## Data Models

- Game
  - id
  - name
  - description
  - round
  - status
- User
  - id
  - username
  - email
  - password
- Player
  - id
  - userId
  - gameId

## APIs

- GET /games
- POST /games
- GET /games/{id}
- PUT /games/{id}
- DELETE /games/{id}
- POST /games/{id}/join      { userId } => PlayerJoined
- POST /games/{id}/start                => GameStarted
- GET /games/{id}/feed       Streaming Response
- POST /users


## Commands

- JoinGameCommand, { gameId, playerId }  => PlayerJoinedEvent
- StartGameCommand, { gameId }           => GameStartedEvent
- LeaveGameCommand, { gameId, playerId } => PlayerLeftEvent
- EliminatePlayerCommand, { gameId, playerId } => PlayerEliminatedEvent
- EndGameComand, { gameId } 		     => GameOverEvent, PlayerWonEvent

## Events

- PlayerJoinedEvent
- PlayerLeftEvent
- PlayerEliminatedEvent
- PlayerWonEvent
- GameStartedEvent
- GameOverEvent

## Queries

- 
- SubscribeGame, { gameId }   

---

## Development Notes

- application.properties
```
server.port=8080

# Enable auto table creation (required if not using in memory db)
spring.jpa.generate-ddl=true

# Enable Access Logs in Tomcat
server.tomcat.accesslog.enabled=true
```

- UUID in Primary Key, Add `@GeneratedValue(generator = "UUID")` with `@Id`

- DTO Pattern with to and fro Conversion
```java
// Using ModelMapper
// in application
@Bean
public ModelMapper modelMapper() {
	return new ModelMapper();
}

// in controller
@Autowired
private ModelMapper modelMapper;

modelMapper.map(post, PostDto.class)

// Using MapStruct
// in gradle.build
implementation 'org.mapstruct:mapstruct:1.4.2.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'

// Create an interface
@Mapper(componentModel = "spring")
public interface GameMapper {
    Game toModel(GameDTO dto);

    GameDTO toDTO(Game game);
}
```

- Unified Response Body
  - Create a Generic DTO for sending out response
  ```java
	@Builder
	@Data
	public class ResponseDTO<T> {
		String message;

		T data;
	}
  ```
  - Create an Advisor which implements `ResponseBodyAdvise`
  ```java
	@RestControllerAdvice(basePackages = "com.vs4vijay.squidgame.controllers")
	public class GenericResponseControllerAdvisor implements ResponseBodyAdvice {

		@Override
		public boolean supports(MethodParameter returnType, Class converterType) {
			// TODO: Check for MimeType
			return true;
		}

		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
			if(body instanceof ResponseDTO || body instanceof ErrorResponseDTO) {
				return body;
			} else {
				return ResponseDTO.builder().data(body).build();
			}
		}
	}
  ```

- Generic Error Response
  - Create an Error Response DTO
  ```java
	@Builder
	@Data
	public class ErrorResponseDTO {
		String message;

		String errorCode;

		@Singular
		List<Object> errors;
	}
  ```
  - Create an Advisor to handle various exceptions
  ```java
	@RestControllerAdvice
	public class ControllerExceptionHandlerAdvisor {

		@ExceptionHandler(Exception.class)
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		public ErrorResponseDTO globalExceptionHandler(Exception ex, WebRequest request) {
			// TODO: Print stacktrace
			ex.printStackTrace();

			ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
					.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
					.message(ex.getMessage())
					.error(ex.getMessage())
					.build();
			return errorDTO;
		}
	}
  ```
  - In order to handle custom exceptions, create an exception extending from RuntimeException
  ```java
	@Data
	public class ResourceNotFoundException extends RuntimeException {
		String id;

		String resourceName;

		public ResourceNotFoundException(String id, String resourceName) {
			super(String.format("%s(%s) not found", resourceName, id));
		}

		public ResourceNotFoundException(String id) {
			this(id, "Resource");
		}

		public ResourceNotFoundException() {
			super("Resource not found");
		}
	}
  ```
  - Handle Custom Exception in Advisor, add method to Advisor
  ```java
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponseDTO resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
				.errorCode(HttpStatus.NOT_FOUND.name())
				.message(ex.getMessage())
				.error(ex.getMessage())
				.build();
		return errorDTO;
	}
  ```

- Validation:
  - Annotate DTOs and Models with `@NotBlank, @NotNull` etc 
  - Apply `@Validated` to the controller, it will throw `ConstraintViolationException` if constraints like `@Min` etc not met in arguments
  - Apply `@Valid` with `@RequestBody` in controller method, it will throw `MethodArgumentNotValidException` if there are validation errors
  - Create an Advisor to handle `ConstraintViolationException`
  ```java
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        System.out.println("ConstraintViolationException ex " + ex);

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }
  ```
  - Create an Advisor to handler `MethodArgumentNotValidException`
  ```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println("methodArgumentNotValidException ex " + ex);

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> String.format("%s %s", x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .message("Validation Error")
                .errors(errors)
                .build();
        return errorDTO;
    }
  ```
  - Ref:
	- https://www.baeldung.com/spring-boot-bean-validation

- Remove empty fields from response - `@JsonInclude(JsonInclude.Include.NON_NULL)`

- Logging: Annotate `@Slf4j` to class, it will inject `log` object for logging

- Populate Audit Fields:
  - Add fields with `@CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy` annotations
  - Use `@Column(updatable = false)` in createdAt and createdBy fields
  - Apply `@EntityListeners(AuditingEntityListener.class)` at Model class
  - Implement `AuditorAware` interface:
  ```java
	public class AuthAuditorAware implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal()
			return Optional.of("Anonymous");
		}
	}
  ```
  - Create AuditorConfig to populate createdBy and updatedBy
  ```java
	@Configuration
	@EnableJpaAuditing(auditorAwareRef = "auditorAware")
	public class AuditConfig {

		@Bean
		public AuditorAware<String> auditorAware() {
			return new AuthAuditorAware();
		}
	}
  ```

- OpenAPI / Swagger Docs
  - Add springdoc dependencies to build.gradle
  ```java
	implementation 'org.springdoc:springdoc-openapi-ui:1.5.11'
  ```
  - Add config to application.properties
  ```
	springdoc.packagesToScan=com.vs4vijay.squidgame.controllers
	springdoc.api-docs.path=/api-docs
	springdoc.swagger-ui.path=/swagger-ui.html
  ```

- Soft Delete
  - Add column to Model `@Column(updatable = false) Boolean isDeleted;`
  - Add SOFT_DELETE_CLAUSE for later use
  ```java
	public static final String SOFT_DELETE_CLAUSE = "is_deleted = false";
  ```
  - Add `@SQLDelete` and `@Where` Annotations to Model
  ```java
	@SQLDelete(sql = "UPDATE game SET is_deleted = true WHERE id = ?")
	@Where(clause = BaseModel.SOFT_DELETE_CLAUSE)
  ```

- Pagination and Sorting
  - Add `Pageable pageRequest` parameter to controller method, as spring injects this automatically
  - Make service method to access Pageable parameter and return Page<> object
  ```java
	public Page<Game> getAll(Pageable pageRequest) {
        return gameRepository.findAll(pageRequest);
    }
  ```
  - Add `Map metadata` to ResponseDTO
  - Build metadata from Page object at controller
  ```java
	Map metadata = Map.of(
		"currentPage", page.getNumber(),
		"totalPages", page.getTotalPages(),
		"pageSize", page.getSize(),
		"totalElements", page.getTotalElements()
	);
  ```

- Event Sourcing and CQRS with Axon Framework
  - Ref:
	- https://docs.axoniq.io/reference-guide/
	- https://www.youtube.com/watch?v=nMXfgJWViws
  - 

- Misc
```shell

gradlew clean
gradlew bootRun


# Auto reload in IntelliJ
https://stackoverflow.com/questions/23155244/spring-boot-hotswap-with-intellij-ide

# Spring Data Query Creation
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.introduction
```