package com.io.greenscan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class WasteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wasteTypeId;

    private String wasteTypeName;

    @OneToMany(mappedBy = "wasteType")
    private List<Waste> wastes = new ArrayList<>();

}
