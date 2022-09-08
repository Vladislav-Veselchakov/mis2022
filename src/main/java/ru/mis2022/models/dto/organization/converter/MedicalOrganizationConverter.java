package ru.mis2022.models.dto.organization.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.MedicalOrganization;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalOrganizationConverter {

    public MedicalOrganizationDto toDto(MedicalOrganization entityMedOrg) {
        MedicalOrganizationDto medicalOrganizationDto = new MedicalOrganizationDto();
        medicalOrganizationDto.setId(entityMedOrg.getId());
        medicalOrganizationDto.setName(entityMedOrg.getName());
        medicalOrganizationDto.setAddress(entityMedOrg.getAddress());

        return medicalOrganizationDto;
    }

    public MedicalOrganization toEntity(MedicalOrganizationDto medicalOrganizationDto) {
        MedicalOrganization medicalOrganization = new MedicalOrganization();
        medicalOrganization.setId(medicalOrganizationDto.getId());
        medicalOrganization.setName(medicalOrganizationDto.getName());
        medicalOrganization.setAddress(medicalOrganizationDto.getAddress());

        return medicalOrganization;
    }

    public List<MedicalOrganizationDto> toListDto(List<MedicalOrganization> medicalOrganizationList) {

        List<MedicalOrganizationDto> medOrgDtoList = new ArrayList<>();
        for(MedicalOrganization medicalOrganization : medicalOrganizationList) {
            medOrgDtoList.add(toDto(medicalOrganization));
        }
        return medOrgDtoList;
    }
}
