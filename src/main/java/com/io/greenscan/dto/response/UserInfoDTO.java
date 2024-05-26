package com.io.greenscan.dto.response;

import com.io.greenscan.entity.ExchangerTicket;
import com.io.greenscan.entity.User;
import com.io.greenscan.entity.UserExchanger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserInfoDTO {

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Long mileageInfo; // 마일리지 정보를 담을 필드
    private String exchangerTicket;

    public UserInfoDTO(String name, String phoneNumber, List<UserExchanger> exchangerTickets, Long mileage, String email, Object o) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password=password;
        this.email = email;
        this.mileageInfo = mileage;
        this.exchangerTicket = exchangerTickets.stream()
                .map(UserExchanger::getExchangerTicket)
                .map(ExchangerTicket::getExchangerTicketName)
                .collect(Collectors.joining(", ")); // 예: 여러 교환권 이름을 콤마로 구분하여 연결

    }

    public UserInfoDTO(User user) {
        this.name = user.getUserName();
        this.phoneNumber= user.getPhoneNumber();
        this.password= user.getPassword();
        this.exchangerTicket=getExchangerTicket();
    }

    // 마일리지 정보 업데이트 메서드
    public void updateMileageInfo(long mileage) {
        this.mileageInfo = mileage;
    }

    // Getter와 Setter 메서드 생략
}


