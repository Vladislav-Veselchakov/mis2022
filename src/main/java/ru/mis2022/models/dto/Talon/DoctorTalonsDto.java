package ru.mis2022.models.dto.Talon;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DoctorTalonsDto(

        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalDateTime time,
        Long patientId,
        String firstName,
        String lastName,
        String surName
) {
}
