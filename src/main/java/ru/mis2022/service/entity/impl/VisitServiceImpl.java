package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Visit;
import ru.mis2022.repositories.VisitRepository;
import ru.mis2022.service.entity.VisitService;


@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public Visit persist(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit merge(Visit visit) {
        return visitRepository.save(visit);
    }
}
