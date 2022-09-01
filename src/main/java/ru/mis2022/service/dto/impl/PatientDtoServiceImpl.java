package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.repositories.PatientRepository;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.dto.PatientDtoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDtoServiceImpl implements PatientDtoService {
    private final PatientRepository patientRepository;
    private final TalonRepository talonRepository;

    @Override
    public CurrentPatientDto getCurrentPatientDtoByEmail(String email) {
        return patientRepository.getCurrentPatientDtoByEmail(email);
    }

    @Override
    public List<TalonDto> findAllByPatientId(Long id) {
        return talonRepository.findAllByPatientIdDto(id);
    }
}
