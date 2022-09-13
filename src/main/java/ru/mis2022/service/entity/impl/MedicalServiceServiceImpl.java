package ru.mis2022.service.entity.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.MedicalService;
import ru.mis2022.repositories.MedicalServiceRepository;
import ru.mis2022.service.entity.MedicalServiceService;


@Service
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class MedicalServiceServiceImpl implements MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    @Override
    public MedicalService save(MedicalService medicalService) {
        return medicalServiceRepository.save(medicalService);
    }

    @Override
    public boolean isExistByIdentifier(String identifier) {
        return medicalServiceRepository.existsByIdentifier(identifier);
    }

    @Override
    public boolean isExistByName(String name) {
        return medicalServiceRepository.existsByName(name);
    }
}
