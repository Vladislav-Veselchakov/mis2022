package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Disease;
import ru.mis2022.repositories.DiseaseRepository;
import ru.mis2022.service.entity.DiseaseService;


@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;

    @Override
    public Disease persist(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public Disease merge(Disease disease) {
        return diseaseRepository.save(disease);
    }
}
