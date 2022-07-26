package ru.mis2022.models.dto.hr;

import java.time.LocalDate;

public record CurrentHrManagerDto(String firstName,
                                  String lastName,
                                  LocalDate birthday,
                                  String roleName) {}
