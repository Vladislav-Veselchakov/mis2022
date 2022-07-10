package ru.mis2022.models.entity;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Talon - Талон на прием к врачу
 * на каждый день их создается 8
 * талон удаляется при явке пациента, и у пациента создается обращение/посещение
 */


public class Talon {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Patient patient;
    private Doctor doctor;
}
