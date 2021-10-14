package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.ErrorResponseDTO;
import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandlerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode("ResourceNotFound")
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO globalExceptionHandler(Exception ex, WebRequest request) {
        // TODO: Print stacktrace
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode("InternalServerError")
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }
}
