package com.io.greenscan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProfileRequestDto {

    private String email;
    private String username;
    private String password; // 추가: 비밀번호 필드
    private String phoneNumber;

    public UpdateProfileRequestDto() {
        // 기본 생성자
    }

}
