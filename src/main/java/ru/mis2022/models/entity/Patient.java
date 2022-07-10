package ru.mis2022.models.entity;


import java.util.List;
/*
    Patient - Пациент.
    может получать лечение у врачей
    может записываться на лечение на 14 дней вперед
    может видеть свои талоны к врачам
    может видеть свои обращения/посещения
 */

public class Patient extends User {
    private String passport;
    private String polis;
    private String snils;
    private String address;
    private List<Talon> talons;
    private List<Appeal> appeals;
}
