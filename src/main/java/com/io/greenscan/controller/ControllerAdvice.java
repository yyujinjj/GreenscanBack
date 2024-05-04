package com.io.greenscan.controller;

import com.io.greenscan.exception.EmailAlreadyExistsException;
import com.io.greenscan.exception.InvalidReferralIdException;
import com.io.greenscan.exception.PasswordsDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)//Validation에서의 예외가 터질 시
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) //body안에는 원하는 데이터를 넣어주면된다.
                .body(e.getBindingResult().getFieldError().getDefaultMessage());//e.getBindingResult().getFieldError().getDefaultMessage())로 인해 필요한 문구만 출력이 가능
    }// entity의 조건들이 잘못된 부분들이 다 잡힌다.

    @ExceptionHandler(PasswordsDoNotMatchException.class)//서버가 돌아가다가 터지면 잡아주는 anotation -> 해당 예외의 경우
    public ResponseEntity<String> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) //코드를 control할 수 있다.
                .body(e.getMessage());

    }

    @ExceptionHandler(InvalidReferralIdException.class)//서버가 돌아가다가 터지면 잡아주는 anotation -> 해당 예외의 경우
    public String handleInvalidReferralIdException(InvalidReferralIdException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)//서버가 돌아가다가 터지면 잡아주는 anotation -> 해당 예외의 경우
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return e.getMessage();
    }
}
