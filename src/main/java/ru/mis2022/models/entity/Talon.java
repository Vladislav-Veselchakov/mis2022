package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Talon - Талон на прием к врачу
 * на каждый день их создается 8
 * талон удаляется при явке пациента, и у пациента создается обращение/посещение
 */


@Entity
public class Talon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

}
