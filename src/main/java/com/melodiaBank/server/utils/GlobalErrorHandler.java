package com.melodiaBank.server.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.melodiaBank.server.exceptions.UserNotFoundException;


@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler({UserNotFoundException.class, IllegalArgumentException.class})
    public Map<String, Object> handleUserNotFoundException(RuntimeException exception){

        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST);

        return error;
    }
}
