package ru.mis2022.service.dto;

import ru.mis2022.models.dto.department.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {
    List<DepartmentDto> findAllByMedicalOrganizationId(Long id);
    List<DepartmentDto> getAllDepartments();
}
