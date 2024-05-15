package com.io.greenscan.dto.response;

import com.io.greenscan.entity.ExchangerTicket;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoDTO {

    private String name;
    private String phoneNumber;
    private String email;

    public UserInfoDTO(String name, String phoneNumber, List<ExchangerTicket> exchangerTickets, Long mileage, String email, Object o) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getter와 Setter 메서드 생략
}


