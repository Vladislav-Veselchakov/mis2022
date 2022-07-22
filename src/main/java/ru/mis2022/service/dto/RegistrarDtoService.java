package ru.mis2022.service.dto;

import ru.mis2022.models.dto.registrar.CurrentRegistrarDto;

public interface RegistrarDtoService {
    CurrentRegistrarDto getCurrentRegistrarDtoByEmail(String email);
}
