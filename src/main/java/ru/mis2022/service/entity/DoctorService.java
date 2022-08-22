package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Doctor;

import java.util.List;


public interface DoctorService {

    Doctor findByEmail(String email);

    Doctor persist(Doctor doctor);

    List<Doctor> findAllByDepartmentId(Long id);

    Doctor findByDoctorId(Long id);

    boolean existsById(long doctorId);

}
