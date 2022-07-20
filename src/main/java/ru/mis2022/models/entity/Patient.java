package ru.mis2022.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
/**
    Patient - Пациент.
    может получать лечение у врачей
    может записываться на лечение на 14 дней вперед
    может видеть свои талоны к врачам
    может видеть свои обращения/посещения
 */

@Entity
@NoArgsConstructor
public class Patient extends User {

    private String passport;

    private String polis;

    private String snils;

    private String address;

    @OneToMany
    private Set<Talon> talons;

    @OneToMany
    private Set<Appeal> appeals;

    public Patient(String email, String password, String firstName, String lastName, @Nullable String surname,
                   LocalDate birthday, Role role, String passport, String polis, String snils, String address) {
        super(email, password, firstName, lastName, surname, birthday, role);
        this.passport = passport;
        this.polis = polis;
        this.snils = snils;
        this.address = address;
    }
}
