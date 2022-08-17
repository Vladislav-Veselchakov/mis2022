package ru.mis2022.controllers.hrManager;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.user.UserDto;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.mapper.UserMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.UserService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/hr_manager")
public class HrManagerPersonalRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    @ApiOperation("find user by firstName and lastName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список сотрудников"),
    })
    @GetMapping("/allUsers")
    public Response<List<UserDto>> findPersonalByFirstAndLastName(
            @RequestParam(required = false, defaultValue = "") String fullName) {
        String fullNames = fullName.replaceAll("\\s+", "%");
        return Response.ok(userMapper.toListDto(
                userService.findPersonalByFullName(fullNames, Role.RolesEnum.PATIENT.name())));
    }
}
