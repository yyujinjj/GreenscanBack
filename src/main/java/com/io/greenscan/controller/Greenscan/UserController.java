package com.io.greenscan.controller.Greenscan;

import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/signup")
    public UserSignUpResponseDTO signup(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO){
        log.info("회원가입 요청 들어옴");

        //비밀번호 유효성 검사
        if (!isValidPassword(userSignUpRequestDTO.getPassword())){
            throw new WeakPasswordException("비밀번호에 대소문자와 특수문자가 포함되어야 합니다.");
        }
        UserSignUpResponseDTO responseDTO=userService.signup(userSignUpRequestDTO);
        return responseDTO;
    }

    private boolean isValidPassword(String password){
        //비밀번호 유효성 검사 로직을 구현
        //여기서는 간단히 대소문자와 특수문자가 포함되어 있는 지만 확인하는 예시를 제시
        return password.matches(".*[a-z].*") // 소문자 포함 여부 확인
                && password.matches(".*[A-Z].*") // 대문자 포함 여부 확인
                && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // 특수문자 포함 여부 확인
    }
}
