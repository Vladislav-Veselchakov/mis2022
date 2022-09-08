package ru.mis2022.models.dto.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mis2022.utils.validation.OnCreate;
import ru.mis2022.utils.validation.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {

    @Null(groups = OnCreate.class, message = "id должен быть равен null")
    @PositiveOrZero(groups = OnUpdate.class, message = "id должен быть положительным или ноль")
    private Long id;

    @NotBlank
    private String name;
}
