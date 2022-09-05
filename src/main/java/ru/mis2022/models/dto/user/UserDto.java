package ru.mis2022.models.dto.user;

import lombok.Builder;

@Builder
public record UserDto(

        Long id,
        String firstName,
        String lastName,
        String surName,
        String birthday,
        String email,
        String roleName
) {}

