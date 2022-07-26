package ru.mis2022.models.dto.administrator;

import java.time.LocalDate;

public record CurrentAdministratorDto(String firstName,
                                      String lastName,
                                      LocalDate birthday,
                                      String roleName) {}
