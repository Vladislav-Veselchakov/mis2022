package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.repositories.AdministratorRepository;
import ru.mis2022.service.entity.AdministratorService;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final PasswordEncoder encoder;

    private final AdministratorRepository administratorRepository;

    @Override
    public Administrator findByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }

    @Override
    public Administrator existById(Long id) {
        return administratorRepository.existById(id);
    }

    @Override
    public Administrator persist(Administrator administrator) {
        administrator.setPassword(encoder.encode(administrator.getPassword()));
        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator merge(Administrator administrator) {
        administrator.setPassword(encoder.encode(administrator.getPassword()));
        return administratorRepository.save(administrator);
    }
}
