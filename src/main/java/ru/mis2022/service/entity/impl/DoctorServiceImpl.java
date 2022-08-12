package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.service.entity.DoctorService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final PasswordEncoder encoder;

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public Doctor persist(Doctor doctor) {
        doctor.setPassword(encoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return doctorRepository.findByDepartment(department);
    }


    @Override
    public List<Doctor> findAllByDepartment_Id(Long id) {
        return doctorRepository.findAllByDepartment_Id(id);
    }

    @Override
    public Doctor existById(Long id) {
        return doctorRepository.existById(id);
    }

}
