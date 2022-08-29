package ru.mis2022.models.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientDto (
    Long id,
    String firstName,
    String lastName,
    String surName,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    LocalDate birthday,
    String passport,
    String polis,
    String snils
    )
{}
