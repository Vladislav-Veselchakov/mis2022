package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.hr.CurrentHrManagerDto;
import ru.mis2022.models.entity.HrManager;

public interface HrManagerRepository extends JpaRepository<HrManager, Long> {

    @Query("""
        SELECT
            hr.firstName AS firstName,
            hr.lastName AS lastName,
            hr.birthday AS birthday,
            r.name AS roleName
        FROM HrManager hr
            JOIN Role r ON hr.role.id = r.id
        WHERE hr.email = :email
        """)
    CurrentHrManagerDto getCurrentHrDtoByEmail(String email);

    HrManager findByEmail(String email);
}
