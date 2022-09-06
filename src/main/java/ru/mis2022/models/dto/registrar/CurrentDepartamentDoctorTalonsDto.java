package ru.mis2022.models.dto.registrar;

import java.time.LocalDateTime;

public record CurrentDepartamentDoctorTalonsDto(
                                    Long departamentId,
                                    String departamentName,
                                    Long doctorId,
                                    String doctorFirstName,
                                    String doctrorLastName,
                                    Long talonId,
                                    LocalDateTime time,
                                    Long patientId,
                                    String patientName


) {}
