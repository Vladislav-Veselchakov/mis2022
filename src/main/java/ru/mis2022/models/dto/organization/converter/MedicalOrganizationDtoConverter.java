package ru.mis2022.models.dto.organization.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.MedicalOrganization;

@Component
public class MedicalOrganizationDtoConverter {
    public MedicalOrganizationDto toDto(MedicalOrganization entity) {
        return MedicalOrganizationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }

    public MedicalOrganization toEntity(MedicalOrganizationDto dto) {
        return MedicalOrganization.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .build();
    }
}
