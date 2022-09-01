package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.registrar.CurrentRegistrarDto;
import ru.mis2022.repositories.RegistrarRepository;
import ru.mis2022.service.dto.RegistrarDtoService;

@Service
@RequiredArgsConstructor
public class RegistrarDtoServiceImpl implements RegistrarDtoService {
    private final RegistrarRepository registrarRepository;

    @Override
    public CurrentRegistrarDto getCurrentRegistrarDtoByEmail(String email) {
        return registrarRepository.getCurrentRegistrarDtoByEmail(email);
    }
}
