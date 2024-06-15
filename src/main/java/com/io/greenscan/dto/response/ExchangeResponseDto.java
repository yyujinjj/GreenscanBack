package com.io.greenscan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponseDto {
    private String message;
    private String userName;
    private String ticketName;
}
