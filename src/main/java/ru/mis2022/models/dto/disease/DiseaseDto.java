package ru.mis2022.models.dto.disease;

import lombok.Builder;

@Builder
public record DiseaseDto(Long id,String identifier,String name) {}
