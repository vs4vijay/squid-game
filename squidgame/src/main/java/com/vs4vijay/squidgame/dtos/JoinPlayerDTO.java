package com.vs4vijay.squidgame.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JoinPlayerDTO {
    @NotBlank
    String name;
}
