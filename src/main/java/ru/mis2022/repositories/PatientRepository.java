package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.entity.Patient;

import java.util.List;

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

    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Patient findPatientById(Long id);


    @Query("""
            SELECT p FROM Patient p
            WHERE LOWER(CONCAT(p.lastName,' ',p.firstName))
                LIKE LOWER(CONCAT('%',:fullName,'%'))
            ORDER BY p.lastName, p.firstName, p.id
            """)
    List<Patient> findPatientByFullName (String fullName);


    @Query("SELECT t.patient.id FROM Talon t WHERE t.id = :talonId")
    Long findPatientIdByTalonId(Long talonId);

}
