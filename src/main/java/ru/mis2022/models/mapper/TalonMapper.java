package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.Talon.TalonDto;
import ru.mis2022.models.entity.Talon;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalonMapper {

    List<TalonDto> toListDto(List<Talon> talons);

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    TalonDto toDto(Talon talon);

}
