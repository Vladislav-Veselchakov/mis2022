package ru.mis2022.models.dto.registrar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TalonsWithoutDoctorDto {
    private Long id;
    private String time;
    private Long patientId;
    private String patientName;
}
