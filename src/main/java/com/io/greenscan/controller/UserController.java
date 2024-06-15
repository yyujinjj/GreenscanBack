package com.io.greenscan.controller;

import com.io.greenscan.dto.request.UpdateProfileRequestDto;
import com.io.greenscan.dto.request.UserLoginRequestDTO;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserInfoDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.service.JwtService;
import com.io.greenscan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 생성한다.
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> signup(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        log.info("회원가입 요청 들어옴");

        UserSignUpResponseDTO responseDTO = userService.signup(userSignUpRequestDTO);

        String referredUserId = userSignUpRequestDTO.getReferralId();
        log.info("추천인에 대한 정보 불러옴");
        if (referredUserId != null && !referredUserId.isEmpty()) {
            userService.processReferral(referredUserId);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse response) {
        log.info("로그인 요청 들어옴");

        String token = userService.login(userLoginRequestDTO);
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getMyPageInfo(HttpServletRequest request) {
        log.info("사용자 정보 요청 들어옴");
        String token = extractTokenFromRequest(request);
        if (token == null) {
            log.error("토큰이 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String userEmail = jwtService.getUserEmail(token);
        if (userEmail == null || !jwtService.validateToken(token, userEmail)) {
            log.error("유효하지 않은 JWT 토큰.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserInfoDTO userInfo = userService.getUserInfo(token);
        return ResponseEntity.ok(userInfo);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    @PostMapping("/update-profile")
    public ResponseEntity<UserInfoDTO> updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto, HttpServletRequest request) {
        // 개인 정보 업데이트 서비스 호출
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
        }
        UserInfoDTO userInfo = userService.updateUserProfile(requestDto, token);
        return ResponseEntity.ok(userInfo);
    }


//    @PostMapping("/update-profile")
//    public ResponseEntity<UserInfoDTO> updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto, HttpServletRequest request) {
//        // 요청에서 토큰을 추출합니다.
//        String token = extractTokenFromRequest(request);
//        log.info("가");
//        if (token == null) {
//            log.error("토큰이 없습니다.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        String userEmail = jwtService.getUserEmail(token);
//        log.info("나");
//        if (userEmail == null || !jwtService.validateToken(token, userEmail)) {
//            log.error("유효하지 않은 JWT 토큰.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        // 토큰과 함께 요청을 전달합니다.
//        UserInfoDTO userInfo = userService.updateUserProfile(requestDto, request);
//        return ResponseEntity.ok(userInfo);
//    }
}