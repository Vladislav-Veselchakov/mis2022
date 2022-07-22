package ru.mis2022.models.dto.registrar;


import java.time.LocalDate;

public interface CurrentRegistrarDto {
    String getFirstName();
    String getLastName();
    LocalDate getBirthday();
    String getRoleName();
}
