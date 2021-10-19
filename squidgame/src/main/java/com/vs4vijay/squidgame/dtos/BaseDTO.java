package com.vs4vijay.squidgame.dtos;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseDTO {
    Boolean isActive;

    Date createdAt;

    Date updatedAt;

    String createdBy;

    String updatedBy;
}
