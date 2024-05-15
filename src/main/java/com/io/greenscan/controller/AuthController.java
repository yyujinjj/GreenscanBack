package com.io.greenscan.controller;

import com.io.greenscan.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@Slf4j
@RestController
@RequiredArgsConstructor

public class AuthController {

    private JwtService jwtService;

    @Autowired
    public  AuthController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @GetMapping("/createToken")
    public String createToken(String userEmail){
        return  jwtService.createToken(userEmail);
    }
}
