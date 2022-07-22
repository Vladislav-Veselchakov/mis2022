package ru.mis2022.service.entity;


import ru.mis2022.models.entity.Diploma;

public interface DiplomaService {

    Diploma persist(Diploma diploma);
    Diploma merge(Diploma diploma);
}
