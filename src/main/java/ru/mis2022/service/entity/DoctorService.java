package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.List;
import java.util.Set;
import ru.mis2022.models.entity.Talon;

import java.util.Set;

import java.util.List;

public interface DoctorService {

    Doctor findByEmail(String email);

    Doctor persist(Doctor doctor);

    List<Doctor> findByDepartment(Department department);

    List<Doctor> findAllByDepartment_Id(Long id);

    Doctor existById(Long id);

    Doctor merge(Set<Talon> talons, Long doctorId);

}
