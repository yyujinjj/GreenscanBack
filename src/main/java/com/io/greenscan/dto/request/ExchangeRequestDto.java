package com.io.greenscan.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRequestDto {
    private String email;
    private Long exchangerTicketId;
    private String password;
    private boolean confirm;

    // Getters and setters
    public boolean isConfirm() {
        return confirm;
    }
}
