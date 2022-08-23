package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.yet.YetDto;
import ru.mis2022.models.entity.Yet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDate.class, YearMonth.class})
public interface YetMapper {

    List<YetDto> toListDto(List<Yet> allYet);

    @Mapping(target = "dayFrom", expression = "java(YearMonth.from(yet.getDayFrom()))")
    @Mapping(target = "dayTo", expression = "java(YearMonth.from(yet.getDayTo()))")
    YetDto toDto(Yet yet);

    @Mapping(target = "dayFrom", expression = "java(yetDto.dayFrom().atDay(1))")
    @Mapping(target = "dayTo", expression = "java(yetDto.dayTo().atEndOfMonth())")
    Yet toEntity(YetDto yetDto);
}
