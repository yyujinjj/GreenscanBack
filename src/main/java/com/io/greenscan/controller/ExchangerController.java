package com.io.greenscan.controller;

import com.io.greenscan.dto.request.ExchangeRequestDto;
import com.io.greenscan.service.ExchangerService;
import com.io.greenscan.service.JwtService;
import com.io.greenscan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exchanger")
@Slf4j
public class ExchangerController {

    @Autowired
    private ExchangerService exchangerService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/exchange")
    public ResponseEntity<?> confirmExchange(@RequestBody ExchangeRequestDto requestDto, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("하나");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("JWT token is missing or invalid.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is missing or invalid.");
        }

        String token = authorizationHeader.substring(7);
        String userEmail = jwtService.getUserEmail(token);
        log.info("둘");
        if (userEmail == null || !jwtService.validateToken(token, userEmail)) {
            log.error("Invalid JWT token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        return exchangerService.processExchangeRequest(requestDto, token);
    }
}
