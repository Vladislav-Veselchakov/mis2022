package ru.mis2022.controllers.doctor;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.patient.PatientConverter;
import ru.mis2022.models.dto.patient.PatientDto;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.PatientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
@RequestMapping("/api/doctor/patient")

public class DoctorPatientRestController {
    private final PatientService patientService;
    private final PatientConverter patientConverter;

    @ApiOperation("Find patient by firstName and lastName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список сотрудников")
    })
    @GetMapping("/stringPattern")
    public Response<List<PatientDto>> findPatientByFullName(
            @RequestParam(required = false, defaultValue = "") String stringPattern) {
        String fullNames = stringPattern.replaceAll("\\s+", "%");
        return Response.ok(patientConverter.toPatientDto(patientService.findPatientByFullName(fullNames)));
    }
}
