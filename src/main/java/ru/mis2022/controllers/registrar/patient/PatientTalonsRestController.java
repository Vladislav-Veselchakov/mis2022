package ru.mis2022.controllers.registrar.patient;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.PatientDtoService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/talons")
public class PatientTalonsRestController {
    private final TalonService talonService;
    private final PatientService patientService;
    private final PatientDtoService patientDtoService;

    @ApiOperation("Авторизованный пациент получает все свои талоны на которые у него есть запись")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получение всех талонов, занятых пациентом")
    })
    //todo list2 пациент должен браться не из id а из авторизации
    @GetMapping("/{patientId}")
    public Response<List<TalonDto>> getAllTalonsByPatientId(@PathVariable Long patientId) {
       ApiValidationUtils
               .expectedTrue(patientService.isExistById(patientId),
                       402, "Пациента с таким id нет!");
       //todo list2 пренести логику из patientDtoService в talonDtoService
       return Response.ok(patientDtoService.findAllByPatientId(patientId));
    }

    @ApiOperation("Пациент удаляет запись к врачу по id талона")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запись по талону удалена"),
            @ApiResponse(code = 402, message = "Талона с таким id нет!"),
            @ApiResponse(code = 403, message = "Пациент не записан по этому талону"),
    })
    @PatchMapping("/{talonId}")
    public Response<Void> cancelRecordTalons(@PathVariable Long talonId) {
        Talon talon = talonService.findTalonById(talonId);
        ApiValidationUtils
                .expectedNotNull(talon, 402, "Талона с таким id нет!");
        long userId = ((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ApiValidationUtils
                .expectedTrue(userId == talonService.findPatientIdByTalonId(talonId),
                        403, "Пациент не записан по этому талону");
        talon.setPatient(null);
        talonService.save(talon);
        return Response.ok();
    }
}
