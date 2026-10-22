package com.melodiaBank.server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.melodiaBank.server.exceptions.EmailAlreadyExistException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public Map<String, Object> handleEmailAlreadyExistException(RuntimeException ex){

        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST);

        return error;
    }
}
