package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.patient.CurrentPatientDto(
            p.firstName,
            p.lastName,
            p.birthday,
            p.passport,
            p.polis,
            p.snils,
            p.address,
            r.name)
        FROM Patient p
            JOIN Role r ON p.role.id = r.id
        WHERE p.email = :email
        """)
    CurrentPatientDto getCurrentPatientDtoByEmail(String email);

    Patient findByEmail(String email);
}
