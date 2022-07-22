package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Vacation;

public interface VacationService {

    Vacation persist(Vacation vacation);
    Vacation merge(Vacation vacation);
}
