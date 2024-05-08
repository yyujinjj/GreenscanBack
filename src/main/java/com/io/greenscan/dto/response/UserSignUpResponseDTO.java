package com.io.greenscan.dto.response;

import com.io.greenscan.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponseDTO {

    private Long userEmail;

    public UserSignUpResponseDTO(User user) {
        this.userEmail = user.getUserId();
    }
}
