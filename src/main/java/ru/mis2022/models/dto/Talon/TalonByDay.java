package ru.mis2022.models.dto.Talon;

import lombok.Builder;

import java.util.List;

@Builder
public record TalonByDay(String date,
                         List<TalonDto> talonsDto) {}
