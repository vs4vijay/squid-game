package com.vs4vijay.squidgame.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
public class CreateGameDTO {
    @NotBlank
    String name;

    String description;

    Integer round;

    Boolean isActive;
}
