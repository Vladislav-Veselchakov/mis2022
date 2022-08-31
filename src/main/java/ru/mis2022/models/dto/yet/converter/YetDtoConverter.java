package ru.mis2022.models.dto.yet.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.yet.YetDto;
import ru.mis2022.models.entity.Yet;

import java.time.YearMonth;

@Component
public class YetDtoConverter {

    public YetDto toDto(Yet entity){
        return YetDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .dayFrom(YearMonth.from(entity.getDayFrom()))
                .dayTo(YearMonth.from(entity.getDayTo()))
                .build();
    }

    public Yet toEntity(YetDto dto){
        return Yet.builder()
                .id(dto.id())
                .price(dto.price())
                .dayFrom(dto.dayFrom().atDay(1))
                .dayTo(dto.dayTo().atEndOfMonth())
                .build();
    }
}
