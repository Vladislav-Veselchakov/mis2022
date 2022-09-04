package ru.mis2022.controllers.economist;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.economist.CurrentEconomistDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.EconomistDtoService;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist")
public class EconomistRestController {
    private final EconomistDtoService economistDtoService;


    @ApiOperation(value = "Авторизованный экономист получает информацию о себе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает экономиста"),
    })
    @GetMapping("/mainPage/current")
    public Response<CurrentEconomistDto> getCurrentEconomistDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(economistDtoService.getCurrentEconomistDtoByEmail(currentUser.getEmail()));
    }
}
