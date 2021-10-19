package com.vs4vijay.squidgame.dtos;

import lombok.Data;

@Data
public class PlayerDTO extends BaseDTO {
    String id;
    String name;
    String gameId;
}
