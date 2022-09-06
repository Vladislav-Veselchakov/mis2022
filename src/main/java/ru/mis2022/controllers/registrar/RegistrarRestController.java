package ru.mis2022.controllers.registrar;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.registrar.RegistrarAndTalonsOnTodayDto;
import ru.mis2022.models.entity.Registrar;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.RegistrarDtoService;

@RestController
@RequestMapping("/api/registrar")
@PreAuthorize("hasRole('REGISTRAR')")
@RequiredArgsConstructor
public class RegistrarRestController {
    private final RegistrarDtoService registrarDtoService;

    @ApiOperation(value = "Авторизованный регистратор получает свое ФИО и список талонов с докторами и департаментами")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает ФИО регистратора со списком талонов всех докторов"),
    })
    @GetMapping("/mainPage/current")
    public Response<RegistrarAndTalonsOnTodayDto> getRegistrarAndTalonsOnTodayDto() {
        String email = ((Registrar) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        return Response.ok(registrarDtoService.getRegistrarAndTalonsOnTodayDto(email));
    }

}
