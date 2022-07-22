package ru.mis2022.service.entity;

import ru.mis2022.models.entity.MedicalService;

public interface MedicalServiceService {

    MedicalService persist(MedicalService medicalService);
    MedicalService merge(MedicalService medicalService);
}
