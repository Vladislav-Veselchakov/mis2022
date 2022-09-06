package ru.mis2022.models.dto.registrar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrarAndTalonsOnTodayDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String roleName;
    private List<DepartmentsWithDoctorsDto> departments;

    public RegistrarAndTalonsOnTodayDto(Long id, String firstName, String lastName, String roleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleName = roleName;
    }
}
