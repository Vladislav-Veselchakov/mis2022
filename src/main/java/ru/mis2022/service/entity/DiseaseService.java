package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Disease;

public interface DiseaseService {
    boolean isExistByIdentifier(String identifier);
    boolean isExistById(Long id);
    void deleteById (Long id);
    Disease save(Disease disease);

}
