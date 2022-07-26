package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.hr.CurrentHrManagerDto;
import ru.mis2022.models.entity.HrManager;

public interface HrManagerRepository extends JpaRepository<HrManager, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.hr.CurrentHrManagerDto(
            hr.firstName,
            hr.lastName,
            hr.birthday,
            r.name)
        FROM HrManager hr
            JOIN Role r ON hr.role.id = r.id
        WHERE hr.email = :email
        """)
    CurrentHrManagerDto getCurrentHrDtoByEmail(String email);

    HrManager findByEmail(String email);
}
