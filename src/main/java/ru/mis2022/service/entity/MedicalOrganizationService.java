package ru.mis2022.service.entity;

import ru.mis2022.models.entity.MedicalOrganization;

import java.util.List;


public interface MedicalOrganizationService {
    MedicalOrganization persist(MedicalOrganization medicalOrganization);

    List<MedicalOrganization> findAll();

    MedicalOrganization existById(Long id);
}
