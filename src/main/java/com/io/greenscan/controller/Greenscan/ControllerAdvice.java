package com.io.greenscan.controller.Greenscan;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException e) {
        return "중복회원가입";
    }
}
