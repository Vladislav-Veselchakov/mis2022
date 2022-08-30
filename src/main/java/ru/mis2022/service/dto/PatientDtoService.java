package ru.mis2022.service.dto;

import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Talon;

import java.util.List;

public interface PatientDtoService {

    CurrentPatientDto getCurrentPatientDtoByEmail(String email);
    List<TalonDto> findAllByPatientId(Long id);

}
