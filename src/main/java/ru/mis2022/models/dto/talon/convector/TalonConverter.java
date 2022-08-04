package ru.mis2022.models.dto.talon.convector;


import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Talon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
public class TalonConverter {

    public List<TalonDto> toTalonDto(Set<Talon> talons, Long doctorId) {
        List<TalonDto> setDto = new ArrayList<>();
        for (Talon talon : talons) {
            setDto.add(TalonDto.builder()
                    .time(talon.getTime())
                    .id(talon.getId())
                    .doctorId(doctorId)
                    .build());
        }
        Collections.sort(setDto, (Comparator.comparingLong(TalonDto::getId)));
        return setDto;
    }
}
