package com.io.greenscan.controller;

import com.io.greenscan.dto.request.ExchangeRequestDto;
import com.io.greenscan.service.ExchangerService;
import com.io.greenscan.service.JwtService;
import com.io.greenscan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchanger")
public class ExchangerController {

    private final ExchangerService exchangerService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public ExchangerController(ExchangerService exchangerService, UserService userService, JwtService jwtService) {
        this.exchangerService = exchangerService;
        this.userService = userService;
        this.jwtService=jwtService;

            }

    @PostMapping("/exchange")

    public ResponseEntity<String> confirmExchange(@RequestBody ExchangeRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");



        String userEmail = jwtService.getUserEmail(token);
        if (userEmail == null) {
            return ResponseEntity.status(401).body("Invalid JWT token");
        }


        // userEmail을 사용하여 서비스 로직 처리
        return exchangerService.processExchangeRequest(requestDto, userEmail);
    }
    private String extractTokenFromRequest(HttpServletRequest request) {
        // HTTP 요청에서 Authorization 헤더에서 토큰 추출
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            // "Bearer " 부분을 제거하고 토큰만 반환
            return token.substring(7);
        }
        return null;
    }
}

