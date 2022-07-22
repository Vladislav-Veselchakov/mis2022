package ru.mis2022.service.dto;

import ru.mis2022.models.dto.doctor.CurrentDoctorDto;

public interface DoctorDtoService {

    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);
}
