package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department persist(Department department);
    List<Department> findAllByMedicalOrganization_Id(Long id);
    Department existById(Long id);
}
