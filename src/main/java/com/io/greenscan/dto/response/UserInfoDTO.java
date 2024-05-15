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
    private Long mileageInfo; // 마일리지 정보를 담을 필드

    public UserInfoDTO(String name, String phoneNumber, List<ExchangerTicket> exchangerTickets, Long mileage, String email, Object o) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.mileageInfo = mileage;
    }

    // 마일리지 정보 업데이트 메서드
    public void updateMileageInfo(long mileage) {
        this.mileageInfo = mileage;
    }

    // Getter와 Setter 메서드 생략
}


