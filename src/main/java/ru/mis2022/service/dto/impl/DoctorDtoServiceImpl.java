package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.service.dto.DoctorDtoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorDtoServiceImpl implements DoctorDtoService {
    private final DoctorRepository doctorRepository;

    @Override public CurrentDoctorDto getCurrentDoctorDtoByEmail(String email) {
        return doctorRepository.getCurrentDoctorDtoByEmail(email);
    }

    @Override
    public List<CurrentDoctorDto> findDoctorDtoByDepartment(Department department) {
        return doctorRepository.findDoctorDtoByDepartment(department);
    }

    @Override
    public List<DoctorDto> findAllByDepartmentId(Long id) {
        return doctorRepository.findAllByDepartmentIdDto(id);
    }

    @Override
    public boolean isExistsById(Long id) {
        return doctorRepository.existsById(id);
    }


}
