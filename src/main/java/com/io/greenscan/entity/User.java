package com.io.greenscan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String Password;

    private String email;

    private String phoneNumber;

    private Long mileage;

    @OneToMany(mappedBy = "user")
    private List<ExchangerTicket> exchangerTickets = new ArrayList<>();
}
