package ru.mis2022.service.dto;

import ru.mis2022.models.dto.administrator.CurrentAdministratorDto;

public interface AdministratorDtoService {

    CurrentAdministratorDto getCurrentAdministratorDtoByEmail(String email);
}
