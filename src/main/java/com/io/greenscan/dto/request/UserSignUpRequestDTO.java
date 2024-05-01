package com.io.greenscan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDTO {

    private String userName;

    private String password;

    private String email;

    private String phoneNumber;
}
