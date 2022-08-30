package ru.mis2022.service.dto;

import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.MedicalOrganization;

import java.util.List;

public interface MedicalOrganizationDtoService {
    List<MedicalOrganizationDto> findAll();
}
