package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Yet;
import ru.mis2022.repositories.YetRepository;
import ru.mis2022.service.entity.YetService;


@Service
@RequiredArgsConstructor
public class YetServiceImpl implements YetService {

    private final YetRepository yetRepository;

    @Override
    public Yet persist(Yet yet) {
        return yetRepository.save(yet);
    }

    @Override
    public Yet merge(Yet yet) {
        return yetRepository.save(yet);
    }
}
