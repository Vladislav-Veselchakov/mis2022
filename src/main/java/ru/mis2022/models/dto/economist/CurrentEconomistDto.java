package ru.mis2022.models.dto.economist;

import java.time.LocalDate;

public interface CurrentEconomistDto {

    String getFirstName();

    String getLastName();

    LocalDate getBirthday();

    String getRoleName();
}
