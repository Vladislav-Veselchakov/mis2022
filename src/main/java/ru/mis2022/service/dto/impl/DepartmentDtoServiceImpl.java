package ru.mis2022.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.repositories.DepartmentRepository;
import ru.mis2022.service.dto.DepartmentDtoService;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentDtoServiceImpl implements DepartmentDtoService {

    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAllByMedicalOrganizationId(Long id) {
        return departmentRepository.findAllByMedicalOrganizationIdDto(id);
    }
}
