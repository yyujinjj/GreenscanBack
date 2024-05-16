package com.io.greenscan.entity;

import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username")
    private String userName;

    private String password;

    private String email;

    private String phoneNumber;

    private Long mileage;

    @OneToMany(mappedBy = "user")
    private List<ExchangerTicket> exchangerTickets = new ArrayList<>();


    public User(UserSignUpRequestDTO userSignUpRequestDTO) {
        this.userName = userSignUpRequestDTO.getUserName();
        this.password = userSignUpRequestDTO.getPassword();
        this.email = userSignUpRequestDTO.getEmail();
        this.phoneNumber = userSignUpRequestDTO.getPhoneNumber();

    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setUserName(String newUsername) {
        this.userName = newUsername;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }




}
