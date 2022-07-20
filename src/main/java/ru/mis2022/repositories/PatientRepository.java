package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("""
        SELECT
            p.firstName AS firstName,
            p.lastName AS lastName,
            p.birthday AS birthday,
            r.name AS roleName
        FROM Patient p
            JOIN Role r ON p.role.id = r.id
        WHERE p.email = :email
        """)
    CurrentPatientDto getCurrentPatientDtoByEmail(String email);

    Patient findByEmail(String email);
}
