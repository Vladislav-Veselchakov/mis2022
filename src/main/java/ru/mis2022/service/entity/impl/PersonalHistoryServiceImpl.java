package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.repositories.PersonalHistoryRepository;
import ru.mis2022.service.entity.PersonalHistoryService;


@Service
@RequiredArgsConstructor
public class PersonalHistoryServiceImpl implements PersonalHistoryService {

    private final PersonalHistoryRepository personalHistoryRepository;

    @Override
    //todo заменить на save()
    public PersonalHistory persist(PersonalHistory personalHistory) {
        return personalHistoryRepository.save(personalHistory);
    }

    @Override
    //todo удалить
    public PersonalHistory merge(PersonalHistory personalHistory) {
        return personalHistoryRepository.save(personalHistory);
    }
}
