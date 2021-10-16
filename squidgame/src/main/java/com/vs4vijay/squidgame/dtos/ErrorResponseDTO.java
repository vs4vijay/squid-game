package com.vs4vijay.squidgame.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ErrorResponseDTO {
    String message;

    String errorCode;

    @Singular
    List<Object> errors;
}
