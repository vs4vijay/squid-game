package com.vs4vijay.squidgame.dtos;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class GameDTO {
    UUID id;

    String name;

    String description;

    Integer round;

    Boolean isActive;

    Date createdAt;

    Date updatedAt;
}
