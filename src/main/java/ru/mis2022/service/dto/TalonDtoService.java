package ru.mis2022.service.dto;

import ru.mis2022.models.dto.talon.TalonDto;

import java.util.List;
import java.util.Optional;

public interface TalonDtoService {

    Optional<List<TalonDto>> findAllByDoctorId(long doctorId);
}
