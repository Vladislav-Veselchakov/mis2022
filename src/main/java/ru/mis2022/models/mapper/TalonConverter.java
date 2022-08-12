package ru.mis2022.models.mapper;


import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.Talon.TalonDto;
import ru.mis2022.models.entity.Talon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//todo перенести в ru.mis2022.models.dto.Talon.converter, заодно имя директории сделай с маленькой буквы
@Component
public class TalonConverter {

    public List<TalonDto> toTalonDtoByDoctorId(List<Talon> talons, Long doctorId) {
        List<TalonDto> setDto = new ArrayList<>();
        for (Talon talon : talons) {
            setDto.add(TalonDto.builder()
                    .time(talon.getTime())
                    .id(talon.getId())
                    .doctorId(doctorId)
                    .build());
        }
        //todo не надо сортировать - пусть это делает фронт
        Collections.sort(setDto, (Comparator.comparingLong(TalonDto::getId)));

        return setDto;
    }

}
