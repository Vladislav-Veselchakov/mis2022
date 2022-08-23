package ru.mis2022.controllers.patient;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.mapper.TalonMapper;
import ru.mis2022.models.response.Response;
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
    private final TalonMapper talonMapper;
    private final PatientService patientService;

    //todo swagger
   @GetMapping("/{patientId}")
    public Response<List<TalonDto>> getAllTalonsByPatientId(@PathVariable Long patientId) {
       ApiValidationUtils
               //todo использовать метод isExist
               .expectedNotNull(patientService.findPatientById(patientId),
                       402, "Пациента с таким id нет!");
       //todo PatientDtoService сразу получать дто
       List<Talon> talons = talonService.findAllByPatientId(patientId);
       return Response.ok(talonMapper.toListDto(talons));
    }

    //todo swagger
    //todo пациент может отменить запись только на себя поэтому не вариант передавать в параметры ИД пациента
    // надо получать текущего пациента и по нему удалять запись
    // надо найти талон по двум параметрам и если он null кинуть эксепшн, а если нет то снять пациента с талона
    @PatchMapping ("/{talonId}/{patientId}")
    public Response<Void> cancelRecordTalons(@PathVariable Long talonId, @PathVariable Long patientId) {

        ApiValidationUtils
                .expectedNotNull(talonService.findTalonById(talonId),
                        402, "Талона с таким id нет!");

        ApiValidationUtils
                .expectedNotNull(patientService.findPatientById(patientId),
                        403, "Пациента с таким id нет!");

        Talon talon = talonService.findTalonById(talonId);
        talon.setPatient(null);
        talonService.save(talon);
        return Response.ok();
    }
}
