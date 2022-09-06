package ru.mis2022.models.dto.doctor.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.registrar.DoctorsWithTalonsDto;
import ru.mis2022.models.dto.registrar.TalonsWithoutDoctorDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DoctorDtoConverter {
    public List<DoctorsWithTalonsDto> groupTalonsByDoctor(List<DoctorsWithTalonsDto> docs) {

        if(docs == null) return null;
        // Группируем талоны по докторам
        Map<DoctorsWithTalonsDto, List<TalonsWithoutDoctorDto>> mapDocs = docs.stream()
                .collect(
                        Collectors.groupingBy(
                                p-> new DoctorsWithTalonsDto(p.getId(), p.getFirstName(), p.getLastName(), null),
                                Collectors.flatMapping(
                                        pp-> pp.getTalons() == null ? Stream.ofNullable(null) :
                                                Stream.of(new TalonsWithoutDoctorDto(
                                                        pp.getTalons().get(0).getId(),
                                                        pp.getTalons().get(0).getTime(),
                                                        pp.getTalons().get(0).getPatientId(),
                                                        pp.getTalons().get(0).getPatientName())),
                                        Collectors.toList()
                                )
                        )
                );

        // После группировки, каждому доктору присваиваем получившийся список талонов и превращаем map в список:
        List<DoctorsWithTalonsDto> docsWitnTalons = (List<DoctorsWithTalonsDto>) ((HashMap) mapDocs).entrySet().stream().map(
                        x-> {
                            DoctorsWithTalonsDto tmp =  (DoctorsWithTalonsDto) ((Map.Entry) x).getKey();
                            if(((List<TalonsWithoutDoctorDto>) ((Map.Entry) x).getValue()).isEmpty()) {
                                // do nothing
                            } else
                                tmp.setTalons( (List<TalonsWithoutDoctorDto>) ((Map.Entry) x).getValue());
                            return tmp;
                        }

                )
                .sorted()
                .collect(Collectors.toList());


        return docsWitnTalons;
    }

}
