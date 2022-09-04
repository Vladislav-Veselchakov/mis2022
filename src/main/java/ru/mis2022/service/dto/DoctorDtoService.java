package ru.mis2022.service.dto;

import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.dto.doctor.DoctorDto;

import java.util.List;

public interface DoctorDtoService {

    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);

    List<DoctorDto> findAllByDepartmentId(Long id);

    boolean isExistsById(Long id);
}
