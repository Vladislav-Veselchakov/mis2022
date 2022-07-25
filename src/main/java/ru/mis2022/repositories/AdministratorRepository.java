package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.dto.administrator.CurrentAdministratorDto;
import ru.mis2022.models.entity.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    @Query("""
        SELECT
            p.firstName AS firstName,
            p.lastName AS lastName,
            p.birthday AS birthday,
            r.name AS roleName
        FROM Administrator p
            JOIN Role r ON p.role.id = r.id
        WHERE p.email = :email
        """)
    CurrentAdministratorDto getCurrentAdministratorDtoByEmail(String email);

    Administrator findByEmail(String email);
}
