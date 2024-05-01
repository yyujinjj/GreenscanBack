package com.io.greenscan.dto.response;

import com.io.greenscan.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponseDTO {

    private Long userId;

    public UserSignUpResponseDTO(User user) {
        this.userId = user.getUserId();
    }
}
