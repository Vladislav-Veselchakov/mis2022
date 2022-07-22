package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.PriceOfMedicalService;
import ru.mis2022.repositories.PriceOfMedicalServiceRepository;
import ru.mis2022.service.entity.PriceOfMedicalServiceService;


@Service
@RequiredArgsConstructor
public class PriceOfMedicalServiceServiceImpl implements PriceOfMedicalServiceService {

    private final PriceOfMedicalServiceRepository priceOfMedicalServiceRepository;

    @Override
    public PriceOfMedicalService persist(PriceOfMedicalService priceOfMedicalService) {
        return priceOfMedicalServiceRepository.save(priceOfMedicalService);
    }

    @Override
    public PriceOfMedicalService merge(PriceOfMedicalService priceOfMedicalService) {
        return priceOfMedicalServiceRepository.save(priceOfMedicalService);
    }
}
