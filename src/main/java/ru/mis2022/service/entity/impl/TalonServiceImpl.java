package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.repositories.TalonRepository;
import ru.mis2022.service.entity.TalonService;


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
}
