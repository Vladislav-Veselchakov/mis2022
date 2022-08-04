package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.Set;

public interface DoctorService {

    Doctor findByEmail(String email);

    Doctor persist(Doctor doctor);

    Doctor merge(Set<Talon> talons, Long doctorId);

}
