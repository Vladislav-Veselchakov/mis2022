package ru.mis2022.service.dto.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.economist.CurrentEconomistDto;
import ru.mis2022.repositories.EconomistRepository;
import ru.mis2022.service.dto.EconomistDtoService;

@Service
@RequiredArgsConstructor
public class EconomistDtoServiceImpl implements EconomistDtoService {
    private final EconomistRepository economistRepository;

    @Override
    public CurrentEconomistDto getCurrentEconomistDtoByEmail(String email) {
        return economistRepository.getCurrentEconomistDtoByEmail(email);
    }
}
