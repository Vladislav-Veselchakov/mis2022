package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.entity.TalonService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {

    private final TalonRepository talonRepository;


    @Override
    public Talon save(Talon talon) {
        return talonRepository.save(talon);
    }

    @Override
    @Transactional
    public List<Talon> persistTalonsForDoctor(Doctor doctor, int numberOfDays, int numbersOfTalons) {

        List<Talon> talons = new ArrayList<>();
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));

        for (int day = 0; day < numberOfDays; day++) {
            for (int hour = 0; hour < numbersOfTalons; hour++) {
                talons.add(talonRepository.save(new Talon(time.plusDays(day).plusHours(hour), doctor)));
            }
        }
        return talons;
    }
    //todo list3 endTime должен учитывать талоны до конца дня
    @Override
    public long findTalonsCountByIdAndDoctor(int numberOfDays, Doctor doctor) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(numberOfDays).with(LocalTime.MAX);

        return talonRepository.findCountTalonsByParameters(doctor.getId(), startTime, endTime);
    }

    @Override
    public List<Talon> findAllByDoctorId(Long id) {
        return talonRepository.findAllByDoctorId(id);
    }

    @Override
    public List<Talon> findAllByPatientId(Long id) {
        return talonRepository.findAllByPatientId(id);
    }

    @Override
    public Talon findTalonById(Long id) {
        return talonRepository.findTalonById(id);
    }
    //todo list3 endTime должен учитывать талоны до конца дня
    @Override
    public List<Doctor> findDoctorsWithTalonsSpecificTimeRange(int countDays, Long departmentId) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(countDays).with(LocalTime.MAX);
        return talonRepository.findDoctorsWithTalonsSpecificTimeRange(startTime,endTime, departmentId);
    }

    @Override
    public List<DoctorTalonsDto> getTalonsByDoctorIdAndDay(
            long doctorId, LocalDateTime startDayTime, LocalDateTime endDayTime) {
        return talonRepository.talonsByDoctorByDay(doctorId, startDayTime, endDayTime);
    }

    public Long findPatientIdByTalonId(Long talonId){
        return talonRepository.findPatientIdByTalonId(talonId);
    };
}
