package ru.mis2022.models.dto.Talon.converter;

import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.Talon.TalonByDay;
import ru.mis2022.models.dto.Talon.TalonDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.mis2022.utils.DateFormatter.DATE_FORMATTER;

@Service
public class TalonDtoConverter {

    public List<TalonByDay> groupByDay(List<TalonDto> dtos) {
        Map<String, List<TalonDto>> map = new HashMap<>();
        for (TalonDto dto : dtos) {
            String date = dto.getTime().toLocalDate().format(DATE_FORMATTER);
            if (map.containsKey(date)) {
                List<TalonDto> list = new ArrayList<>(map.get(date));
                list.add(dto);
                map.put(date, list);
            } else {
                map.put(date, List.of(dto));
            }
        }
        List<TalonByDay> result = new ArrayList<>();
        for (String date : map.keySet()) {
            result.add(new TalonByDay(date, map.get(date)));
        }
        result.sort(Comparator.comparing(TalonByDay::date));
        return result;
    }
}
