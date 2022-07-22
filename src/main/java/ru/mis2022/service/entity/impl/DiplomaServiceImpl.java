package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Diploma;
import ru.mis2022.repositories.DiplomaRepository;
import ru.mis2022.service.entity.DiplomaService;


@Service
@RequiredArgsConstructor
public class DiplomaServiceImpl implements DiplomaService {

    private final DiplomaRepository diplomaRepository;

    @Override
    public Diploma persist(Diploma diploma) {
        return diplomaRepository.save(diploma);
    }

    @Override
    public Diploma merge(Diploma diploma) {
        return diplomaRepository.save(diploma);
    }
}
