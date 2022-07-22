package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mis2022.models.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
