package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.disease.DiseaseDto;
import ru.mis2022.models.entity.Disease;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Query("""
            SELECT new ru.mis2022.models.dto.disease.DiseaseDto(
                d.id,
                d.identifier,
                d.name)
            FROM Disease d
            """)
    List<DiseaseDto> findAllDiseaseDto();

    boolean existsByIdentifier(String identifier);

}
