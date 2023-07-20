package com.eCom.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public String HandlerResourceNotFoundException(ResourceNotFoundException ex){
        return ex.getMessage();
    }
}
