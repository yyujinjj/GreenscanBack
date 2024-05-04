package com.io.greenscan.controller.Greenscan;

public class PasswordsDoNotMatchException extends RuntimeException{

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
