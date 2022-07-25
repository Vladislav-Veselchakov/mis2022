package ru.mis2022.models.dto.hr;

import java.time.LocalDate;

public interface CurrentHrManagerDto {
    String getFirstName();
    String getLastName();
    LocalDate getBirthday();
    String getRoleName();
}
