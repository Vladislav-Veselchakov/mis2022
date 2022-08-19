package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Attestation;
import ru.mis2022.repositories.AttestationRepository;
import ru.mis2022.service.entity.AttestationService;


@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements AttestationService {

    private final AttestationRepository attestationRepository;

    @Override
    public Attestation save(Attestation attestation) {
        return attestationRepository.save(attestation);
    }
}
