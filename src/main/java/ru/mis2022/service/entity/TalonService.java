package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.Set;

public interface TalonService {

    Talon persist(Talon talon);
    Talon merge(Talon talon);

    long findTalonsCountByIdAndDoctor(int countDays, Doctor doctor);

    Set<Talon> persistTalonsForDoctor(Doctor doctor, int numberOfDays, int numbersOfTalons);

}
