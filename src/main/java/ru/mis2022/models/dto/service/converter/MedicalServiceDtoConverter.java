package ru.mis2022.models.dto.service.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.service.MedicalServiceDto;
import ru.mis2022.models.entity.MedicalService;

@Component
public class MedicalServiceDtoConverter {

    public MedicalServiceDto toMedicalServiceDto(MedicalService medicalService) {
        return MedicalServiceDto.builder()
                .identifier(medicalService.getIdentifier())
                .name(medicalService.getName())
                .id(medicalService.getId())
                .build();
    }
}
