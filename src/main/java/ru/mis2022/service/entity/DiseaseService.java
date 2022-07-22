package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Disease;

public interface DiseaseService {

    Disease persist(Disease disease);
    Disease merge(Disease disease);
}
