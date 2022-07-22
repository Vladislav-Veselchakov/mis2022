package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Yet;

public interface YetService {

    Yet persist(Yet yet);
    Yet merge(Yet yet);
}
