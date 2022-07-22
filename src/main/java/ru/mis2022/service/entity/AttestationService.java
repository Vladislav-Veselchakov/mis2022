package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Attestation;

public interface AttestationService {

    Attestation persist(Attestation attestation);
    Attestation merge(Attestation attestation);
}
