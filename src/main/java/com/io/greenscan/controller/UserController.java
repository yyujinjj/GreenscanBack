package com.io.greenscan.controller;

import com.io.greenscan.dto.request.UserLoginRequestDTO;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")// body안에 어떤 객체가 들어갈 것인지를 ~Entity<ooo> 안에 명시한다.
    public ResponseEntity<UserSignUpResponseDTO> signup(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO){
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
        boolean loginSuccess = userService.login(userLoginRequestDTO);

        if (loginSuccess) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}
