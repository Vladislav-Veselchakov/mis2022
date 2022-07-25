package ru.mis2022.controllers.administrator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.administrator.CurrentAdministratorDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.AdministratorDtoService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/administrator")
@Api(tags = "Administrator")

public class AdministratorRestController {

    private final AdministratorDtoService administratorDtoService;

    @ApiOperation(value = "This method is used to get the administrator.")
    @GetMapping("/mainPage/current")
    public Response<CurrentAdministratorDto> getCurrentAdministratorDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(administratorDtoService.getCurrentAdministratorDtoByEmail(currentUser.getEmail()));
    }

}
