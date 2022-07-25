package ru.mis2022.service.dto;

import ru.mis2022.models.dto.hr.CurrentHrManagerDto;

public interface HrManagerDtoService {
    CurrentHrManagerDto getCurrentHrDtoByEmail(String email);
}
