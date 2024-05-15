package com.io.greenscan.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {

    @NotNull(message = "이메일은 필수 입력 값 입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "대문자 1개, 소문자 1개, 특수문자 1개 이상 포함하세요.")
    private String password;


}
