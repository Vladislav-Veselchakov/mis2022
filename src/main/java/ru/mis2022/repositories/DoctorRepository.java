package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
        SELECT
            p.firstName AS firstName,
            p.lastName AS lastName,
            p.birthday AS birthday,
            r.name AS roleName,
            d.name AS departmentName            
        FROM Doctor p
            JOIN Role r ON p.role.id = r.id
            JOIN Department d ON p.department.id = d.id
        WHERE p.email = :email
        """)
    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);

    Doctor findByEmail(String email);
}
