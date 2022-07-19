package ru.mis2022.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.repositories.MedicalOrganizationRepository;
import ru.mis2022.service.MedicalOrganizationService;


@Service
@RequiredArgsConstructor
public class MedicalOrganizationServiceImpl implements MedicalOrganizationService {

    private final MedicalOrganizationRepository medicalOrganizationRepository;

    @Override
    public MedicalOrganization persist(MedicalOrganization medicalOrganization) {
        return medicalOrganizationRepository.save(medicalOrganization);
    }
}
