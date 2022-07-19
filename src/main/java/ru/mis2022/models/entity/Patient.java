package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;
/**
    Patient - Пациент.
    может получать лечение у врачей
    может записываться на лечение на 14 дней вперед
    может видеть свои талоны к врачам
    может видеть свои обращения/посещения
 */

@Entity
public class Patient extends User {

    private String passport;

    private String polis;

    private String snils;

    private String address;

    @OneToMany
    private Set<Talon> talons;

    @OneToMany
    private Set<Appeal> appeals;

}
