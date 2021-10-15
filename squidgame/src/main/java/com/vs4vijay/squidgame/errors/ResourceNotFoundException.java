package com.vs4vijay.squidgame.errors;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    String id;

    String resourceName;

    public ResourceNotFoundException(String id, String resourceName) {
        super(String.format("%s(%s) not found", resourceName, id));
    }

    public ResourceNotFoundException(String id) {
        this(id, "Resource");
    }
}
