package com.vs4vijay.squidgame.dtos;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;

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

    String createdBy;

    String updatedBy;
}
