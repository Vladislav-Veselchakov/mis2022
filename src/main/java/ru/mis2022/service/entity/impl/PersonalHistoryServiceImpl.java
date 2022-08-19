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
    public PersonalHistory save(PersonalHistory personalHistory) {
        return personalHistoryRepository.save(personalHistory);
    }
}
