package ru.mis2022.models.dto.registrar;


import java.time.LocalDate;

public record CurrentRegistrarDto(String firstName,
                                  String lastName,
                                  LocalDate birthday,
                                  String roleName) {}
