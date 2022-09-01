package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.repositories.MedicalOrganizationRepository;
import ru.mis2022.service.dto.MedicalOrganizationDtoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalOrganizationDtoServiceImpl implements MedicalOrganizationDtoService {
    private final MedicalOrganizationRepository medicalOrganizationRepository;

    @Override
    public List<MedicalOrganizationDto> findAll() {
        return medicalOrganizationRepository.findAllDto();
    }
}
