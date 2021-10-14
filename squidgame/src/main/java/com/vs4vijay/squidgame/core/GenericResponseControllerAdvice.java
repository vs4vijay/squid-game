package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.ErrorResponseDTO;
import com.vs4vijay.squidgame.dtos.ResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GenericResponseControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        System.out.println("returnType " + returnType);
        System.out.println("converterType " + converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // TODO: Try with new
        if(body instanceof ErrorResponseDTO) {
            return body;
        } else {
            return ResponseDTO.builder().data(body).build();
        }
    }
}
