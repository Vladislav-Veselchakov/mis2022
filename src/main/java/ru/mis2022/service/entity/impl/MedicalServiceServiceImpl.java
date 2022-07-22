package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.MedicalService;
import ru.mis2022.repositories.MedicalServiceRepository;
import ru.mis2022.service.entity.MedicalServiceService;


@Service
@RequiredArgsConstructor
public class MedicalServiceServiceImpl implements MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    @Override
    public MedicalService persist(MedicalService medicalService) {
        return medicalServiceRepository.save(medicalService);
    }

    @Override
    public MedicalService merge(MedicalService medicalService) {
        return medicalServiceRepository.save(medicalService);
    }
}
