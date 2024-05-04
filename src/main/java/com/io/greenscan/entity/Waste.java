package com.io.greenscan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Waste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wasteId;

    private String wasteName;

    private String wasteSize;

    private Long wastePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wasteType_id")
    private WasteType wasteType;

}
