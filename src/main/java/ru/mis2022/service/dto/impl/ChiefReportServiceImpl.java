package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.doctor.ChiefReportDto;
import ru.mis2022.models.dto.doctor.CurrentChiefReportDto;
import ru.mis2022.models.dto.talon.TalonsBusyTotal;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.service.dto.ChiefReportService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiefReportServiceImpl implements ChiefReportService {
    private final DoctorRepository docRepository;

    @Override
    public List<ChiefReportDto> getReport(LocalDate dateStart, LocalDate dateEnd) {
        Doctor signedInDoc = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long deptId = docRepository.getDepartmentIdByDoctorId(signedInDoc.getId());

        List<CurrentChiefReportDto> dataset = docRepository.getWorkloadEmployeesReport(
                deptId, dateStart.atTime(LocalTime.MIN), dateEnd.atTime(LocalTime.MAX));

        // схлопываем (группируем) список по докторам, а талоны складываем в список (List):
        Map<ChiefReportDto, List<TalonsBusyTotal>> mpDocGrouped = dataset.stream()
                .collect(
                        Collectors.groupingBy(
                                p-> new ChiefReportDto(p.doctorId(), p.doctorFullName()) ,
                                Collectors.mapping(
                                        pp->{
                                            return new TalonsBusyTotal(pp.date(), pp.busyTalons(), pp.totalTalons());
                                        },
                                        Collectors.toList()
                                )
                        )
                );

        // После группировки, каждому доктору присваиваем получившийся список талонов. И сортируем по id-доктора:
        List<ChiefReportDto> listReport = (List<ChiefReportDto>) ((HashMap) mpDocGrouped).entrySet().stream().map(
                        x-> {
                            ChiefReportDto tmp11 =  (ChiefReportDto) ((Map.Entry) x).getKey();
                            tmp11.setTalons( (List<TalonsBusyTotal>) ((Map.Entry) x).getValue());
                            return tmp11;
                        }

                )
                .sorted()
                .collect(Collectors.toList());


        return listReport;
    }
}
