package ru.mis2022.service.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.TalonService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {

    private final TalonRepository talonRepository;

    @Override
    public Talon persist(Talon talon) {
        return talonRepository.save(talon);
    }

    @Override
    public Talon merge(Talon talon) {
        return talonRepository.save(talon);
    }

    @Override
    @Transactional
    public Set<Talon> persistTalonsForDoctor(Doctor doctor, int numberOfDays, int numbersOfTalons) {

        Set<Talon> talons = new HashSet<>();
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));

        for (int day = 0; day < numberOfDays; day++){
            for (int hour = 0; hour < numbersOfTalons; hour++) {
                talons.add(talonRepository.save(new Talon(time.plusDays(day).plusHours(hour), doctor)));
            }
        }
        return talons;
    }

    @Override
    public long findTalonsCountByIdAndDoctor(int numberOfDays, Doctor doctor) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(numberOfDays);

        return talonRepository.findCountTalonsByParameters(doctor.getId(), startTime, endTime);
    }
}
