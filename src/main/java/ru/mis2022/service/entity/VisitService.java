package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Visit;

public interface VisitService {

    Visit persist(Visit visit);
    Visit merge(Visit visit);
}
