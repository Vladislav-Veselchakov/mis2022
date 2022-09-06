package ru.mis2022.models.dto.registrar;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class DoctorsWithTalonsDto implements Comparable{
    private  Long id;
    private  String firstName;
    private  String lastName;
    private List<TalonsWithoutDoctorDto> talons;

    public DoctorsWithTalonsDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int compareTo(Object o) {
        DoctorsWithTalonsDto param = (DoctorsWithTalonsDto) o;
        int result = this.lastName.compareTo(param.getLastName());
        if (result == 0) {
            result = this.firstName.compareTo(param.getFirstName());
        }
        return result;
    }
}
