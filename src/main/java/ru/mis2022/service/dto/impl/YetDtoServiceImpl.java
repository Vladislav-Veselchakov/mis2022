package ru.mis2022.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.yet.YetDto;
import ru.mis2022.repositories.YetRepository;
import ru.mis2022.service.dto.YetDtoService;

import java.util.List;

@Service
@AllArgsConstructor
public class YetDtoServiceImpl implements YetDtoService {
    private YetRepository yetRepository;

    @Override
    public List<YetDto> findAll() {
        return yetRepository.findAllDto();
    }
}
