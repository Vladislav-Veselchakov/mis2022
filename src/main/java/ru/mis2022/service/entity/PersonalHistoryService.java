package ru.mis2022.service.entity;

import ru.mis2022.models.entity.PersonalHistory;

public interface PersonalHistoryService {

    PersonalHistory persist(PersonalHistory personalHistory);
    PersonalHistory merge(PersonalHistory personalHistory);
}
