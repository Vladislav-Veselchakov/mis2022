package ru.mis2022.service.dto;

import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.dto.talon.TalonDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TalonDtoService {

    Optional<List<TalonDto>> findAllByDoctorId(long doctorId);

    List<TalonDto> findTalonsByDoctorIdAndTimeBetween(Long doctorId, LocalDateTime timeNow, LocalDateTime timeEnd);
    List<DoctorTalonsDto> getTalonsByDoctorIdAndDay(long doctorId, LocalDateTime startDayTime,
                                                    LocalDateTime endDayTime);
}
