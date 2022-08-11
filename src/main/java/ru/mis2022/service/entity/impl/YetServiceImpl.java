package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Yet;
import ru.mis2022.repositories.YetRepository;
import ru.mis2022.service.entity.YetService;
import java.time.LocalDate;
import java.util.List;


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

    @Override
    public Yet existById(Long id) {
        return yetRepository.existById(id);
    }

    @Override
    public List<Yet> existYetDayFromDayTo(LocalDate dayFrom, LocalDate dayTo) {
        return yetRepository.existYetDayFromDayTo(dayFrom, dayTo);
    }

    @Override
    public List<Yet> findAll() {
        return yetRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        yetRepository.deleteById(id);
    }

    @Override
    public List<Yet> existYetDayFromDayToExceptCurrentId(Long id, LocalDate dayFrom, LocalDate dayTo) {
        return yetRepository.existYetDayFromDayToExceptCurrentId(id, dayFrom, dayTo);
    }

}
