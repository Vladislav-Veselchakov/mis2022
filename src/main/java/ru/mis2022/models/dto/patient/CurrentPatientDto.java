package ru.mis2022.models.dto.patient;

import java.time.LocalDate;

public record CurrentPatientDto(String firstName,
                                String lastName,
                                LocalDate birthday,
                                String passport,
                                String polis,
                                String snils,
                                String address,
                                String roleName) {}