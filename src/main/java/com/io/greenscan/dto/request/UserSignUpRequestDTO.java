package com.io.greenscan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDTO {

    @NotNull(message = "사용자의 이름은 필수 입력 값 입니다.")
    private String userName;

    private String password;

    private String confirmPassword;

    private String email;

    private String phoneNumber;

    private String referralId;
}
