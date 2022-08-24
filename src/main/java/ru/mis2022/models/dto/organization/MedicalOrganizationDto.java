package ru.mis2022.models.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mis2022.utils.validation.OnCreate;
import ru.mis2022.utils.validation.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalOrganizationDto {

    @Null(groups = OnCreate.class, message = "id должен быть равен null")
    @Positive(groups = OnUpdate.class, message = "id должен быть положительным")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
