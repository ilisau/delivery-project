package com.example.dp.web.controller;

import com.example.dp.domain.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException() {

    }
}
