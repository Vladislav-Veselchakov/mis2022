package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.repositories.PatientRepository;
import ru.mis2022.service.dto.PatientDtoService;

@Service
@RequiredArgsConstructor
public class PatientDtoServiceImpl implements PatientDtoService {

    private final PatientRepository patientRepository;

    @Override public CurrentPatientDto getCurrentPatientDtoByEmail(String email) {
        return patientRepository.getCurrentPatientDtoByEmail(email);
    }
}
