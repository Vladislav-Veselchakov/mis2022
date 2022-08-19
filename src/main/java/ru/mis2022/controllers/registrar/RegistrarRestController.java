package ru.mis2022.controllers.registrar;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.registrar.CurrentRegistrarDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.RegistrarDtoService;

@RestController
@RequestMapping("/api/registrar")
@PreAuthorize("hasRole('REGISTRAR')")
@RequiredArgsConstructor
public class RegistrarRestController {

    private final RegistrarDtoService registrarDtoService;

    @GetMapping("/mainPage/current")
    //todo добавить описание сваггера
    @ApiOperation(value = "This method is used to get current registrar.")
    public Response<CurrentRegistrarDto> getCurrentRegistrarDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(registrarDtoService.getCurrentRegistrarDtoByEmail(currentUser.getEmail()));
    }

}
