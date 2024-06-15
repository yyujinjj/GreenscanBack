package com.io.greenscan.controller;

import com.io.greenscan.dto.request.GameLevelRequestDto;
import com.io.greenscan.service.GameService;
import com.io.greenscan.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final JwtService jwtService;

    @PostMapping("/complete-game-level")
    public ResponseEntity<String> completeGameLevel(@RequestBody GameLevelRequestDto request,
                                                    @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            log.info("Token is missing.");
            return ResponseEntity.status(401).body("Authorization token is missing");
        }

        String result = gameService.completeGameLevel(token, request.getCompletedLevel());
        return ResponseEntity.ok(result);
    }
}