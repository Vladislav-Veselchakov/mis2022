package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Yet;
import java.time.LocalDate;
import java.util.List;

public interface YetService {

    Yet persist(Yet yet);
    Yet merge(Yet yet);

    Yet existById(Long id);

    List<Yet> existYetDayFromDayTo(LocalDate dayFrom, LocalDate dayTo);

    List<Yet> findAll();

    void deleteById(Long id);

    List<Yet> existYetDayFromDayToExceptCurrentId(Long id, LocalDate dayFrom, LocalDate dayTo);

}
