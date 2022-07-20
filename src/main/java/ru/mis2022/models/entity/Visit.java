package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
