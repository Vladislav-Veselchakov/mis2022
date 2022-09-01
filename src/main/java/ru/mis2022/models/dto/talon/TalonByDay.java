package ru.mis2022.models.dto.talon;

import lombok.Builder;

import java.util.List;

@Builder
public record TalonByDay(String date, List<TalonDto> talonsDto) {}
