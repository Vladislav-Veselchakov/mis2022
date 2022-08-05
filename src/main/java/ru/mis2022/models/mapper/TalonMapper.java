package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.Talon.TalonDto;
import ru.mis2022.models.entity.Talon;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TalonMapper {

   @Mapping(source = "currentDoctorDto.id", target = "doctorId")
    Set<TalonDto> toSetDto(Set<Talon> talons);

   List<TalonDto> toListDto(List<Talon> talons);

    @Mapping(source = "doctor.id", target = "doctorId")
    TalonDto toDto(Talon talon);
}
