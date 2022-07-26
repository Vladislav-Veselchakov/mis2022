package ru.mis2022.models.dto.economist;

import java.time.LocalDate;

public record CurrentEconomistDto(String firstName,
                                  String lastName,
                                  LocalDate birthday,
                                  String roleName) {}
