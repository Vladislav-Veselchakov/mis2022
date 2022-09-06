package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.department.converter.DepartmentDtoConverter;
import ru.mis2022.models.dto.doctor.converter.DoctorDtoConverter;
import ru.mis2022.models.dto.registrar.CurrentDepartamentDoctorTalonsDto;
import ru.mis2022.models.dto.registrar.DepartmentsWithDoctorsDto;
import ru.mis2022.models.dto.registrar.RegistrarAndTalonsOnTodayDto;
import ru.mis2022.repositories.RegistrarRepository;
import ru.mis2022.service.dto.RegistrarDtoService;
import ru.mis2022.service.dto.TalonDtoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrarDtoServiceImpl implements RegistrarDtoService {
    private final RegistrarRepository registrarRepository;
    private final TalonDtoService talonDtoService;
    private final DepartmentDtoConverter departmentDtoConverter;
    private final DoctorDtoConverter doctorDtoConverter;


    @Override
    public RegistrarAndTalonsOnTodayDto getRegistrarAndTalonsOnTodayDto(String pEmail) {
        // Пишем текущего юзера:
        RegistrarAndTalonsOnTodayDto result = registrarRepository.getCurrentRegistrarDtoByEmail(pEmail);

        // dataset с департаментом, доктором, талоном
        List<CurrentDepartamentDoctorTalonsDto> currentDeptDoc =
                        talonDtoService.getCurrentDepartamentDoctorTalonsDto(LocalDate.now().atTime(LocalTime.MIN),
                                                                            LocalDateTime.now().with(LocalTime.MAX));

        // Группируем записи по департаменту:
        List<DepartmentsWithDoctorsDto> depts = departmentDtoConverter.groupDoctorsByDeparment(currentDeptDoc);

        // Группируем талоны по доктору и кладем список докторов в департамент:
        depts.stream()
            .forEach(x->{
                    x.setDoctors(doctorDtoConverter.groupTalonsByDoctor(x.getDoctors()));
                }
            );

        result.setDepartments(depts);
        return result;
    }
}
