package com.vs4vijay.squidgame.dtos;

import com.vs4vijay.squidgame.enums.GameStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class GameDTO extends BaseDTO {
    String id;

    String name;

    String description;

    Integer round;

    GameStatus status;
}
