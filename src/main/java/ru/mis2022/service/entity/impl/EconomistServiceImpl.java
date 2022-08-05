package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Economist;
import ru.mis2022.repositories.EconomistRepository;
import ru.mis2022.service.entity.EconomistService;

@Service
@RequiredArgsConstructor
public class EconomistServiceImpl implements EconomistService {
    private final EconomistRepository economistRepository;private final PasswordEncoder encoder;
    @Override
    public Economist findByEmail(String email) {return economistRepository.findByEmail(email);
    }
    @Override
    public Economist persist(Economist economist) {
        economist.setPassword(encoder.encode(economist.getPassword()));
        return economistRepository.save(economist);
    }
}
