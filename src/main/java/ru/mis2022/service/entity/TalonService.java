package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Talon;

import java.util.List;


public interface TalonService {

    Talon persist(Talon talon);
    Talon merge(Talon talon);

    List<Talon> findAllByDoctorId(Long id);

    long findTalonsCountByIdAndDoctor(int countDays, Doctor doctor);

    List<Talon> persistTalonsForDoctorAndPatient(Doctor doctor, Patient patient, int numberOfDays, int numbersOfTalons);

    List<Talon> findAllByPatientId(Long id);

    Talon isExistById(Long id);

    List<Doctor> findDoctorsWithTalonsSpecificTimeRange(int countDays, Long departmentId);

}
