package com.vs4vijay.squidgame.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ResponseDTO<T> {
    String message;

    T data;

    Map metadata;
}
