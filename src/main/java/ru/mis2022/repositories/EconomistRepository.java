package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.economist.CurrentEconomistDto;
import ru.mis2022.models.entity.Economist;

public interface EconomistRepository extends JpaRepository<Economist, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.economist.CurrentEconomistDto(
            p.firstName,
            p.lastName,
            p.birthday,
            r.name)
        FROM Economist p
            JOIN Role r ON p.role.id = r.id
        WHERE p.email = :email
        """)
    CurrentEconomistDto getCurrentEconomistDtoByEmail(String email);

    Economist findByEmail(String email);
}
