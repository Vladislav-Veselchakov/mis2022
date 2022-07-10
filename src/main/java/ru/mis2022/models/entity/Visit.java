package ru.mis2022.models.entity;


import java.time.LocalDate;
import java.util.List;

/**
 * Visit Посещение
 * Каждый раз, когда пациент приходит на прием в рамках обращения, врач оказывает услуги.
 */

public class Visit {
    private Long id;
    private LocalDate dayOfVisit;
    private Doctor doctor;
    private Appeal appeal;
    private List<MedicalService> medicalServices;
}
