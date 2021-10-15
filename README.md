# Squid Game

---

## Tech. Stack

- Java
- Spring 5 with Spring Boot 2
- H2 Database
- Lombok

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
- POST /users
- POST /games/{id}/join      { userId } => Player.JOINED
- POST /games/{id}/start                => Game.STARTED
- GET /games/{id}/feed       Streaming Response

## Events

- Player.JOINED
- Player.LEFT
- Player.ELIMINATED
- Player.WON
- Game.STARTED
- Game.OVER

---

## Development Notes

- application.properties
```

# Enable auto table creation (required if not using in memory db)
spring.jpa.generate-ddl=true

```

- UUID in Primary Key - `@GeneratedValue(generator = "UUID")`

- DTO Pattern and Conversion
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

- Unified Response body

- Generic Success and Error Response

- Validation
Add validation dependencies (org.springframework.boot:spring-boot-starter-validation)
can use @NotNull, @Nullable

- Swagger

- Localization

- Misc
```shell

gradlew clean
gradlew bootRun


# Auto reload in IntelliJ
https://stackoverflow.com/questions/23155244/spring-boot-hotswap-with-intellij-ide


```