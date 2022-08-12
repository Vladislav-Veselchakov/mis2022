package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Patient;


public interface PatientService {

    Patient findByEmail(String email);
    Patient persist(Patient patient);
    Patient isExistById(Long id);
}
