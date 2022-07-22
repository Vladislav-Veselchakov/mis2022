package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.service.dto.DoctorDtoService;

@Service
@RequiredArgsConstructor
public class DoctorDtoServiceImpl implements DoctorDtoService {

    private final DoctorRepository doctorRepository;

    @Override public CurrentDoctorDto getCurrentDoctorDtoByEmail(String email) {
        return doctorRepository.getCurrentDoctorDtoByEmail(email);
    }
}
