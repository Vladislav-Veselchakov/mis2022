package ru.mis2022.models.dto.patient;

import java.time.LocalDate;

public interface CurrentPatientDto {
    String getFirstName();
    String getLastName();
    LocalDate getBirthday();
    String getRoleName();
}
