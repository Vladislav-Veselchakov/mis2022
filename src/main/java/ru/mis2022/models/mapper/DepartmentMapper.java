package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.models.entity.Department;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    List<DepartmentDto> toListDto(List<Department> departments);

    DepartmentDto toDto(Department department);

    Department toEntity(DepartmentDto departmentDto);
}
