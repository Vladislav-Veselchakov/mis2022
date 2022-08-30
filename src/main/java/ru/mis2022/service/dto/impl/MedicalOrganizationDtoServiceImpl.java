package ru.mis2022.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.repositories.MedicalOrganizationRepository;
import ru.mis2022.service.dto.MedicalOrganizationDtoService;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicalOrganizationDtoServiceImpl implements MedicalOrganizationDtoService {
    MedicalOrganizationRepository medicalOrganizationRepository;
    @Override
    public List<MedicalOrganizationDto> findAll() {
        return medicalOrganizationRepository.findAllDto();
    }
}
