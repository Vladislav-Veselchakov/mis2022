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
    //todo заменить на save()
    public Visit persist(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    //todo удалить
    public Visit merge(Visit visit) {
        return visitRepository.save(visit);
    }
}
