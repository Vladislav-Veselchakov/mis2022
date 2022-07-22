package ru.mis2022.service.dto;

import ru.mis2022.models.dto.economist.CurrentEconomistDto;

public interface EconomistDtoService {

    CurrentEconomistDto getCurrentEconomistDtoByEmail(String email);
}
