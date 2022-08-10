package ru.mis2022.models.dto.doctor;

import java.time.LocalDate;

public record CurrentDoctorDto(String firstName,
                               String lastName,
                               LocalDate birthday,
                               String roleName,
                               String departmentName
                               ) {}

