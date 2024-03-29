package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.ErrorResponseDTO;
import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandlerAdvisor {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        System.out.println("ConstraintViolationException ex " + ex);

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println("methodArgumentNotValidException ex " + ex);

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> String.format("%s %s", x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .message("Validation Error")
                .errors(errors)
                .build();
        return errorDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO globalExceptionHandler(Exception ex, WebRequest request) {
        // TODO: Print stacktrace
        ex.printStackTrace();

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .build();
        return errorDTO;
    }
}
