package com.io.greenscan.controller;

import com.io.greenscan.dto.request.UpdateProfileRequestDto;
import com.io.greenscan.dto.request.UserLoginRequestDTO;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserInfoDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 생성한다.
public class UserController {

    private final UserService userService;



    @PostMapping("/signup")// body안에 어떤 객체가 들어갈 것인지를 ~Entity<ooo> 안에 명시한다.
    public ResponseEntity<UserSignUpResponseDTO> signup(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        log.info("회원가입 요청 들어옴"); //Valid를 해야만 requestDTO에 해놨던 제약 조건이 걸린다.

        // 회원 가입 처리
        UserSignUpResponseDTO responseDTO = userService.signup(userSignUpRequestDTO);

        // 추천인 처리
        String referredUserId = userSignUpRequestDTO.getReferralId();
        log.info("추천인에 대한 정보 불러옴");
        if (referredUserId != null && !referredUserId.isEmpty()) {
            userService.processReferral(referredUserId);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse response) {
        log.info("로그인 요청 들어옴");

        // 로그인 처리
//        boolean loginSuccess = Boolean.parseBoolean(userService.login(userLoginRequestDTO));  //true, false 값으로 파싱 자체가 불가능함.

        String token = userService.login(userLoginRequestDTO);
//        if (loginSuccess) {
        response.setHeader("Authorization", token);
        return ResponseEntity.ok("로그인 성공");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
//        }
    }

    //    // 사용자 정보 조회 엔드포인트 추가
    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getMyPageInfo(HttpServletRequest request) {
        log.info("이거는 되야될텐데/...");
        String token = request.getHeader("Authorization"); //토큰 꺼내오기
        if (token == null) {
            log.info("토큰은 들어갔을라나..");
            return ResponseEntity.status(401).body(null); // 또는 적절한 에러 메시지
        }
//        String token = request.getHeader("Authorization"); //토큰 꺼내오기
//        System.out.println(token);
        UserInfoDTO userInfo = userService.getUserInfo(token);
        return ResponseEntity.ok(userInfo);
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


    @PostMapping("/update-profile")
    public ResponseEntity<UserInfoDTO> updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto, HttpServletRequest request) {
        // 개인 정보 업데이트 서비스 호출
        UserInfoDTO userInfo = userService.updateUserProfile(requestDto, request);
        return ResponseEntity.ok(userInfo);
    }
}
