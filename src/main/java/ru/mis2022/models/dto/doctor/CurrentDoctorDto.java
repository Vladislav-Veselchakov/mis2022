package ru.mis2022.models.dto.doctor;

import java.time.LocalDate;

public interface CurrentDoctorDto {

    String getFirstName();

    String getLastName();

    LocalDate getBirthday();

    String getRoleName();

    String getDepartmentName();
}
