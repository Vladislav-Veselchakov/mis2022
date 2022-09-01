package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.hr.CurrentHrManagerDto;
import ru.mis2022.repositories.HrManagerRepository;
import ru.mis2022.service.dto.HrManagerDtoService;

@Service
@RequiredArgsConstructor
public class HrManagerManagerDtoServiceImpl implements HrManagerDtoService {
    private final HrManagerRepository hrManagerRepository;

    @Override
    public CurrentHrManagerDto getCurrentHrDtoByEmail(String email) {
        return hrManagerRepository.getCurrentHrDtoByEmail(email);
    }
}
