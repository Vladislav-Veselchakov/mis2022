package ru.mis2022.models.entity;


import javax.persistence.Entity;
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

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Appeal appeal;

    @OneToMany
    private Set<MedicalService> medicalServices;


}
