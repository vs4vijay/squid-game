package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.ErrorResponseDTO;
import com.vs4vijay.squidgame.dtos.ResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GenericResponseControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        System.out.println("methodParameter.getContainingClass().isAnnotationPresent(RestController.class)=="+ returnType.getContainingClass().isAnnotationPresent(RestController.class));
        System.out.println("returnType " + returnType);
        System.out.println("converterType " + converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResponseDTO || body instanceof ErrorResponseDTO) {
            return body;
        } else {
            // TODO: Try with new
            return ResponseDTO.builder().data(body).build();
        }
    }
}
