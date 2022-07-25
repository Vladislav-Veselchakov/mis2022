package ru.mis2022.models.dto.administrator;

import java.time.LocalDate;

public interface CurrentAdministratorDto {
    String getFirstName();
    String getLastName();
    LocalDate getBirthday();
    String getRoleName();
}
