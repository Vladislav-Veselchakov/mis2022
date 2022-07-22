package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mis2022.models.entity.MedicalService;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
}
