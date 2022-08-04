package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Talon;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TalonMapper {

   @Mapping(source = "currentDoctorDto.id", target = "doctorId")
    Set<TalonDto> toSetDto(Set<Talon> talons);
}
