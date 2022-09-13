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
    public boolean isExistByIdentifier(String identifier) {
        return diseaseRepository.existsByIdentifier(identifier);
    }

    @Override
    public boolean isExistById(Long id) {
        return diseaseRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        diseaseRepository.deleteById(id);
    }

    @Override
    public Disease save(Disease disease) {
        return diseaseRepository.save(disease);
    }

}
