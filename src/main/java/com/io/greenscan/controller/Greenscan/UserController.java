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
        UserSignUpResponseDTO responseDTO = userService.signup(userSignUpRequestDTO);
        return responseDTO;
    }

}
