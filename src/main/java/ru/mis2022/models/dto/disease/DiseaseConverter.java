package ru.mis2022.models.dto.disease;

import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Disease;

//todo положить в директорию converter
@Component
public class DiseaseConverter {

    public DiseaseDto toDiseaseDto(Disease disease) {
        return DiseaseDto.builder()
                .identifier(disease.getIdentifier())
                .name(disease.getName())
                .id(disease.getId())
                .build();
    }
}
