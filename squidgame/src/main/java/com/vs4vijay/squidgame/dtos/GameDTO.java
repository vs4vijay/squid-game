package com.vs4vijay.squidgame.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class GameDTO {
    UUID id;

    @NotBlank
    String name;

    String description;

    Integer round;
}
