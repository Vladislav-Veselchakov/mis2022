package ru.mis2022.models.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Visit Посещение
 * Каждый раз, когда пациент приходит на прием в рамках обращения, врач оказывает услуги.
 */

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private LocalDate dayOfVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Appeal appeal;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<MedicalService> medicalServices;


}
