# Squid Game

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
// in application
@Bean
public ModelMapper modelMapper() {
	return new ModelMapper();
}

// in controller
@Autowired
private ModelMapper modelMapper;

modelMapper.map(post, PostDto.class)

```

- Generic Success and Error Response

- Validation


- Swagger

- Misc
```shell

gradlew clean
gradlew bootRun


# Auto reload in IntelliJ
https://stackoverflow.com/questions/23155244/spring-boot-hotswap-with-intellij-ide


```