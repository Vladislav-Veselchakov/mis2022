package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.registrar.CurrentRegistrarDto;
import ru.mis2022.models.entity.Registrar;

public interface RegistrarRepository extends JpaRepository<Registrar, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.registrar.CurrentRegistrarDto(
            reg.firstName,
            reg.lastName,
            reg.birthday,
            r.name)
        FROM Registrar reg
            JOIN Role r ON reg.role.id = r.id
        WHERE reg.email = :email
        """)
    CurrentRegistrarDto getCurrentRegistrarDtoByEmail(String email);

    Registrar findByEmail(String email);
}
