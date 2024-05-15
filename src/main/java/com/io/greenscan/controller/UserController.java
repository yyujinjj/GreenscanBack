package com.io.greenscan.controller;

import com.io.greenscan.dto.request.UserLoginRequestDTO;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserInfoDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")// body안에 어떤 객체가 들어갈 것인지를 ~Entity<ooo> 안에 명시한다.
    public ResponseEntity<UserSignUpResponseDTO> signup(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        log.info("회원가입 요청 들어옴"); //Valid를 해야만 requestDTO에 해놨던 제약 조건이 걸린다.

        // 회원 가입 처리
        UserSignUpResponseDTO responseDTO = userService.signup(userSignUpRequestDTO);
        return ResponseEntity.status(HttpStatus.OK) //프론트에게 코드를 보내는 역할.
                .body(responseDTO);//ResposeEntity에는 status와 데이터를 같이 보낼 수 있다.
        //status를 하면 안에 코드를 집어 넣을 수 있다. HttpStatus.OK로 넣을 수 있다. Status에 대한 코드가 날라간다.
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
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
        String token = request.getHeader("Authorization"); //토큰 꺼내오기
        System.out.println(token);
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
}
