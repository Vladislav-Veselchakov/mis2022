package ru.mis2022.models.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalOrganizationDto {

    @Positive(message = "id должен быть положительным")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
