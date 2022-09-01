package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.administrator.CurrentAdministratorDto;
import ru.mis2022.repositories.AdministratorRepository;
import ru.mis2022.service.dto.AdministratorDtoService;

@Service
@RequiredArgsConstructor
public class AdministratorDtoServiceImpl implements AdministratorDtoService {
    private final AdministratorRepository administratorRepository;

    @Override
    public CurrentAdministratorDto getCurrentAdministratorDtoByEmail(String email) {
        return administratorRepository.getCurrentAdministratorDtoByEmail(email);
    }
}
