package ru.mis2022.service.entity;

import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.entity.Doctor;

import java.util.List;


public interface DoctorService {

    Doctor findByEmail(String email);

    Doctor persist(Doctor doctor);
    Doctor merge(Doctor doctor);

    List<Doctor> findAllByDepartmentId(Long id);

    Doctor findByDoctorId(Long id);

    boolean isExistsById(long doctorId);

    List<DoctorDto>findDoctorsDtoByDepartmentId(Long departmentId);

}
