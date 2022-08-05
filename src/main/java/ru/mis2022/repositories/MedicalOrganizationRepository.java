package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.entity.MedicalOrganization;

import java.util.List;

public interface MedicalOrganizationRepository extends JpaRepository<MedicalOrganization, Long> {

    List<MedicalOrganization> findAll();

    @Query("""
            SELECT m FROM MedicalOrganization m WHERE m.id = :id
            """)
    MedicalOrganization existById(Long id);
}
