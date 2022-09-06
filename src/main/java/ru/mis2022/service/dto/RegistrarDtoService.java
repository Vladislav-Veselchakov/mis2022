package ru.mis2022.service.dto;

import ru.mis2022.models.dto.registrar.RegistrarAndTalonsOnTodayDto;

public interface RegistrarDtoService {
    RegistrarAndTalonsOnTodayDto getRegistrarAndTalonsOnTodayDto(String email);
}
