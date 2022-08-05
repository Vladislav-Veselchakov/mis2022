package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    List<DoctorDto> toListDto(List<Doctor> doctor);

    @Mapping(target = "birthday", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "password", expression = "java(null)")
    @Mapping(source = "role.name", target = "role")
    @Mapping(source = "department.name", target = "department")
    DoctorDto toDto(Doctor doctor);

    @Mapping(source = "role", target = "role")
    @Mapping(source = "department", target = "department")
    @Mapping(source = "doctorDto.id", target = "id")
    @Mapping(target = "birthday", dateFormat = "dd.MM.yyyy")
    Doctor toEntity(DoctorDto doctorDto, Role role, Department department);
}
