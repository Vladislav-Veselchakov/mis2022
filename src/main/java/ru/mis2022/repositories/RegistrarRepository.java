package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.registrar.RegistrarAndTalonsOnTodayDto;
import ru.mis2022.models.entity.Registrar;

public interface RegistrarRepository extends JpaRepository<Registrar, Long> {

    @Query("""
    SELECT new ru.mis2022.models.dto.registrar.RegistrarAndTalonsOnTodayDto(
    rg.id,
    rg.firstName,
    rg.lastName,
    r.name
    )
    FROM Registrar rg
    INNER JOIN  Role r
        on  r.id = rg.role.id
    WHERE rg.email = :email
    """)
    RegistrarAndTalonsOnTodayDto getCurrentRegistrarDtoByEmail(String email);

    Registrar findByEmail(String email);

}
