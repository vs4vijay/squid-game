package com.vs4vijay.squidgame.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerDTO extends BaseDTO {
    UUID id;
    String name;
}