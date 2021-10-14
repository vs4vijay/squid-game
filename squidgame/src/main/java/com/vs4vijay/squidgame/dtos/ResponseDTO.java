package com.vs4vijay.squidgame.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@Data
public class ResponseDTO<T> {
    String message;

    T data;
}
