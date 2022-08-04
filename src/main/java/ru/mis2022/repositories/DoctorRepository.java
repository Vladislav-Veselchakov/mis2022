package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.doctor.CurrentDoctorDto(
            p.firstName,
            p.lastName,
            p.birthday,
            r.name,
            d.name)
        FROM Doctor p
            JOIN Role r ON p.role.id = r.id
            JOIN Department d ON p.department.id = d.id
        WHERE p.email = :email
        """)
    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);

    Doctor findByEmail(String email);
}
