package com.nutrition.store.nutrition_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class resourceNotFoundException extends RuntimeException {
    
    private final Map<String, String> errors;
    private final String resourceName;
    private final String fieldName;
    private final String fieldValue;

    public resourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("No se encontró %s con %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        
        this.errors = new HashMap<>();
        this.errors.put("message", getMessage());
        this.errors.put("resource", resourceName);
        this.errors.put("field", fieldName);
        this.errors.put("value", fieldValue);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}