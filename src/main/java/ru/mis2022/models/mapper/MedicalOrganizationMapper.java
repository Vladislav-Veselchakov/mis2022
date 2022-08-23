package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.MedicalOrganization;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalOrganizationMapper {

    List<MedicalOrganizationDto> toListDto(List<MedicalOrganization> medicalOrganizations);

    MedicalOrganizationDto toDto(MedicalOrganization medicalOrganization);

    MedicalOrganization toEntity(MedicalOrganizationDto medicalOrganizationDto);

}
