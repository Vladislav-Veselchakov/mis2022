package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.registrar.CurrentDepartamentDoctorTalonsDto;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.dto.TalonDtoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TalonDtoServiceImpl implements TalonDtoService {
    private final TalonRepository talonRepository;

    @Override
    public Optional<List<TalonDto>> findAllByDoctorId(long doctorId) {
        return talonRepository.findAllDtoByDoctorId(doctorId);
    }

    @Override
    public List<TalonDto> findTalonsByDoctorIdAndTimeBetween(Long doctorId, LocalDateTime timeNow, LocalDateTime timeEnd) {
        return talonRepository.findTalonsByDoctorIdAndTimeBetween(doctorId, timeNow, timeEnd);
    }

    @Override
    public List<TalonDto> findAllByPatientId(long patientId) {
        return talonRepository.findAllDtoByPatientId(patientId);
    }


    @Override
    public List<DoctorTalonsDto> getTalonsByDoctorIdAndDay(
            long doctorId, LocalDateTime startDayTime, LocalDateTime endDayTime) {
        return talonRepository.talonsByDoctorByDay(doctorId, startDayTime, endDayTime);
    }

    @Override
    public List<CurrentDepartamentDoctorTalonsDto> getCurrentDepartamentDoctorTalonsDto(LocalDateTime timeStrart, LocalDateTime timeEnd) {
        return talonRepository.getCurrentDepartamentDoctorTalonsDto(timeStrart, timeEnd);
    }
}
