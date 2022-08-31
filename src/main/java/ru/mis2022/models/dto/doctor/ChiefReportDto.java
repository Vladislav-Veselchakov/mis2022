package ru.mis2022.models.dto.doctor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mis2022.models.dto.talon.TalonsBusyTotal;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ChiefReportDto implements Comparable{
    private Long doctorId;
    private String DoctorFullName;

    public ChiefReportDto(Long doctorId, String doctorFullName) {
        this.doctorId = doctorId;
        DoctorFullName = doctorFullName;
    }

    private List<TalonsBusyTotal> talons = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        ChiefReportDto pDoc = (ChiefReportDto)o;
        return this.doctorId > pDoc.getDoctorId() ? 1 : this.doctorId == pDoc.getDoctorId() ? 0 : -1;
    }
}
