package ru.mis2022.controllers.patient;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.PatientDtoService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient")
public class PatientRestController {
    private final PatientDtoService patientDtoService;

    @ApiOperation("get a patient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает пациента"),
    })
    @GetMapping("/mainPage/current")
    public Response<CurrentPatientDto> getCurrentPatientDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(patientDtoService.getCurrentPatientDtoByEmail(currentUser.getEmail()));
    }
}
