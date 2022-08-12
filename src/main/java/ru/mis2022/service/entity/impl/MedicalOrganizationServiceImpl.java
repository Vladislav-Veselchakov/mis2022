package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.repositories.MedicalOrganizationRepository;
import ru.mis2022.service.entity.MedicalOrganizationService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MedicalOrganizationServiceImpl implements MedicalOrganizationService {

    private final MedicalOrganizationRepository medicalOrganizationRepository;

    @Override
    //todo заменить на save()
    public MedicalOrganization persist(MedicalOrganization medicalOrganization) {
        return medicalOrganizationRepository.save(medicalOrganization);
    }

    @Override
    public List<MedicalOrganization> findAll() {
        return medicalOrganizationRepository.findAll();
    }

    @Override
    public MedicalOrganization existById(Long id) {
        return medicalOrganizationRepository.existById(id);
    }
}
