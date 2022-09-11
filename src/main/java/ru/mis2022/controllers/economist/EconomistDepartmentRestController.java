package ru.mis2022.controllers.economist;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.DepartmentDtoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist/departments")
public class EconomistDepartmentRestController {
    private final DepartmentDtoService departmentDtoService;

    @ApiOperation(value = "Экономист получает все отделения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Экономист получил все отделения")
    })
    @GetMapping
    public Response<List<DepartmentDto>> getAllDepartments() {
        return Response.ok(departmentDtoService.getAllDepartments());
    }
}
