package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.dto.TalonDtoService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TalonDtoServiceImpl implements TalonDtoService {

    private final TalonRepository talonRepository;

    @Override
    public Optional<List<TalonDto>> findAllByDoctorId(long doctorId) {
        return talonRepository.findAllDtosByDoctorId(doctorId);
    }
}
