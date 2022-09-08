package ru.mis2022.models.dto.department.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.dto.registrar.CurrentDepartamentDoctorTalonsDto;
import ru.mis2022.models.dto.registrar.DepartmentsWithDoctorsDto;
import ru.mis2022.models.dto.registrar.DoctorsWithTalonsDto;
import ru.mis2022.models.dto.registrar.TalonsWithoutDoctorDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.mis2022.utils.DateFormatter.DATE_TIME_FORMATTER;

@Component
public class DepartmentDtoConverter {
    public List<DepartmentsWithDoctorsDto> groupDoctorsByDeparment(List<CurrentDepartamentDoctorTalonsDto> currentDeptDoc){
        // Группируем список по департаментам, докторов складываем в список (List), а талоны: так как пока группировка
        // только департаменту, то в List-ах  будет лежать столько докторов, сколько и талонов. Поэтому в каждого
        // доктора кладем по одному талону, что бы потом произвести ещё одну группировку
        Map<DepartmentsWithDoctorsDto, List<DoctorsWithTalonsDto>> mpDeptGrouped = currentDeptDoc.stream()
                .filter(p-> p.departamentId() != null)
                .collect(
                        Collectors.groupingBy(
                                p-> new DepartmentsWithDoctorsDto(p.departamentId(), p.departamentName()),
                                Collectors.flatMapping(
                                        pp-> pp.doctorId() == null ? Stream.ofNullable(null):
                                                Stream.of(createDoctorWithTalonsDto(pp)),
                                        Collectors.toList()
                                )
                        )
                );

        // После группировки, каждому департаменту присваиваем получившийся список докторов с талонами
        List<DepartmentsWithDoctorsDto> depts = (List<DepartmentsWithDoctorsDto>) ((HashMap) mpDeptGrouped).entrySet()
                .stream()
                .map(
                        x-> {
                            DepartmentsWithDoctorsDto tmp =  (DepartmentsWithDoctorsDto) ((Map.Entry) x).getKey();
                            if(((List<DoctorsWithTalonsDto>)((Map.Entry)x).getValue()).isEmpty()){
                                // do nothing
                            }
                            else
                                tmp.setDoctors( (List<DoctorsWithTalonsDto>) ((Map.Entry) x).getValue());
                            return tmp;
                        }
                )
                .sorted()
                .collect(Collectors.toList());

        return depts;
    }

    public DoctorsWithTalonsDto createDoctorWithTalonsDto(CurrentDepartamentDoctorTalonsDto pp) {
        if(pp.talonId() == null)
            return new DoctorsWithTalonsDto(pp.doctorId(), pp.doctorFirstName(), pp.doctrorLastName());

        return new DoctorsWithTalonsDto(pp.doctorId(), pp.doctorFirstName(), pp.doctrorLastName(),
                List.of(
                        new TalonsWithoutDoctorDto(
                                pp.talonId(),
                                pp.time() == null? null : DATE_TIME_FORMATTER.format(pp.time()) ,
                                pp.patientId(),
                                pp.patientName()
                        )
                )
        );
    }


        public DepartmentDto toDto(Department entityDepartment) {
            DepartmentDto  departmentDto = new DepartmentDto();
            departmentDto.setId(entityDepartment.getId());
            departmentDto.setName(entityDepartment.getName());

            return departmentDto;
        }
    }
