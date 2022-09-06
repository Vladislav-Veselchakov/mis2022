package ru.mis2022.models.dto.registrar;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.mis2022.models.dto.doctor.ChiefReportDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class DepartmentsWithDoctorsDto implements Comparable{
    private Long id;
    private  String name;
    private List<DoctorsWithTalonsDto> doctors;

    public DepartmentsWithDoctorsDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        DepartmentsWithDoctorsDto pDoc = (DepartmentsWithDoctorsDto)o;
        return this.name.compareTo(pDoc.getName());
    }

}
