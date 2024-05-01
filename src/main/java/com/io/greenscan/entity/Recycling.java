package com.io.greenscan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Recycling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recyclingId;

    private String recyclingName;

    private String recyclingContent;
}
