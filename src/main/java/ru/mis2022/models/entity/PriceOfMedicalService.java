package ru.mis2022.models.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PriceOfMedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double yet;

    private LocalDate dayFrom;

    private LocalDate dayTo;

    @ManyToOne(fetch = FetchType.LAZY)
    private MedicalService medicalService;
}
