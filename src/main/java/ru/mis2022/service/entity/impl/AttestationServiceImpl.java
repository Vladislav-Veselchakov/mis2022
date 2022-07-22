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
    public Attestation persist(Attestation attestation) {
        return attestationRepository.save(attestation);
    }

    @Override
    public Attestation merge(Attestation attestation) {
        return attestationRepository.save(attestation);
    }
}
