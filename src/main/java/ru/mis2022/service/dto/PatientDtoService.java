package ru.mis2022.service.dto;

import ru.mis2022.models.dto.patient.CurrentPatientDto;

public interface PatientDtoService {

    CurrentPatientDto getCurrentPatientDtoByEmail(String email);

}
