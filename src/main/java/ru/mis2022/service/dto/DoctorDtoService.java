package ru.mis2022.service.dto;

import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.List;
import java.util.Set;

public interface DoctorDtoService {

    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);
    List<CurrentDoctorDto> findDoctorDtoByDepartment(Department department);
    List<DoctorDto> findAllByDepartmentId(Long id);


    boolean isExistsById(Long id);
}
