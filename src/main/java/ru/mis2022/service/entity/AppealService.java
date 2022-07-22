package ru.mis2022.service.entity;


import ru.mis2022.models.entity.Appeal;

public interface AppealService {

    Appeal persist(Appeal appeal);
    Appeal merge(Appeal appeal);
}
