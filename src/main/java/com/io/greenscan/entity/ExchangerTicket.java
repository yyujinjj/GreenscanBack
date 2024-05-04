package com.io.greenscan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ExchangerTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangerTicketId;

    private String exchangerTicketName;

    private String exchangerTicketPrice;

    private String exchangerTicketBacode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}