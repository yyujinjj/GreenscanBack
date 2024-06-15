package com.io.greenscan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ExchangerTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchanger_ticket_id")  // 여기서 컬럼명을 명시적으로 매핑
    private Long exchangerTicketId;

    private String exchangerTicketName;

    private Long exchangerTicketPrice;

    private String exchangerTicketBarcode;

    @OneToMany(mappedBy = "exchangerTicket")
    private List<UserExchanger> userExchangers = new ArrayList<>();
}