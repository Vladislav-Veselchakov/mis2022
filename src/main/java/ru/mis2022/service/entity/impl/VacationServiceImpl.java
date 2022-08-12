package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Vacation;
import ru.mis2022.repositories.VacationRepository;
import ru.mis2022.service.entity.VacationService;


@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    @Override
    //todo заменить на save()
    public Vacation persist(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

    @Override
    //todo удалить
    public Vacation merge(Vacation vacation) {
        return vacationRepository.save(vacation);
    }
}
