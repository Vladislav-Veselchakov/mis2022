package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Talon;

public interface TalonService {

    Talon persist(Talon talon);
    Talon merge(Talon talon);
}
