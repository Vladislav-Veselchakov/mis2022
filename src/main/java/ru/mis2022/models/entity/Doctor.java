package ru.mis2022.models.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Doctor - Врач
 * состоит в отделении, оказывает услуги пациентам
 * создает свое расписание, которое продляется на 28 дней вперед
 * Каждый рабочий день выдает 8 талонов
 * может записывать к себе пациентов на 28 дней вперед
 * <p>
 * Заведующий отделением
 * это врач у которого есть функционал смотреть статистику в рамках его отделения
 * оказывает медицинские услуги как остальные врачи
 * <p>
 * Главный врач
 * это врач у которого есть функционал смотреть статистику в рамках ЛПУ/отделений
 * оказывает медицинские услуги как остальные врачи
 * на него не создается расписание
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToOne
    private PersonalHistory personalHistory;

    @OneToMany
    private Set<Talon> talons;

}
