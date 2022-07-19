package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class PriceOfMedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double yet;

    private LocalDate dayFrom;

    private LocalDate dayTo;

    @ManyToOne
    private MedicalService medicalService;
}
