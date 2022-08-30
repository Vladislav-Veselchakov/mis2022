package ru.mis2022.controllers.patient;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.mapper.DepartmentMapper;
import ru.mis2022.models.mapper.DoctorMapper;
import ru.mis2022.models.mapper.MedicalOrganizationMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.DepartmentDtoService;
import ru.mis2022.service.dto.MedicalOrganizationDtoService;
import ru.mis2022.service.dto.DoctorDtoService;
import ru.mis2022.service.dto.TalonDtoService;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping(value = "/api/patient")
public class PatientScheduleRestController {

    @Value("${mis.property.patientSchedule}")
    private Integer numberOfDays;

    private final DepartmentService departmentService;
    private final DepartmentDtoService departmentDtoService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final MedicalOrganizationDtoService medicalOrganizationDtoService;
    private final MedicalOrganizationMapper medicalOrganizationMapper;
    private final DepartmentMapper departmentMapper;
    private final DoctorMapper doctorMapper;
    private final TalonService talonService;
    private final TalonDtoService talonDtoService;
    private final DoctorDtoService doctorDtoService;


    @ApiOperation("get all medical organizations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список медицинских организаций"),
            @ApiResponse(code = 414, message = "Список медицинских организаций пуст")
    })
    @GetMapping(value = "/medicalOrganizations")
    public Response<List<MedicalOrganizationDto>> getAllMedicalOrganization() {
        return Response.ok(medicalOrganizationDtoService.findAll());
    }

    @ApiOperation("get all departments by medical organization id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список департаментов"),
            @ApiResponse(code = 414, message = "Медицинской организации с таким id нет"),
            @ApiResponse(code = 415, message = "У медицинской организации нет департаментов")
    })
    @GetMapping(value = "/medicalOrganization/{medOrgId}/getAllDepartments")
    public Response<List<DepartmentDto>> getAllDepartmentsByMedicalMedicalOrganizationId(@PathVariable Long medOrgId) {
        ApiValidationUtils
                .expectedTrue(medicalOrganizationService.isExistById(medOrgId),
                        414, "Медицинской организации с таким id нет");
        return Response.ok(departmentDtoService.findAllByMedicalOrganizationId(medOrgId));
    }

    @ApiOperation("get all doctors by department id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список всех докторов с пустыми талонами"),
            @ApiResponse(code = 414, message = "Департамента с таким id нет")
    })
    @GetMapping("/departments/{departmentId}/getAllDoctors")
    public Response<List<DoctorDto>> getAllDoctorsByDepartmentsId(@PathVariable Long departmentId) {
        ApiValidationUtils
                .expectedTrue(departmentService.isExistById(departmentId),
                        414, "Департамента с таким id нет!");
        return Response.ok(doctorMapper.toListDto(
                talonService.findDoctorsWithTalonsSpecificTimeRange(numberOfDays, departmentId)));
    }


    @ApiOperation("get all talons by doctor id and between dates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список всех свободных талонов доктора"),
            @ApiResponse(code = 414, message = "Доктора с таким id нет"),
    })
    @GetMapping("/departments/{doctorId}/getFreeTalons")
    public Response<List<TalonDto>> getAllTalonsByDoctorId(@PathVariable Long doctorId) {
        ApiValidationUtils
                .expectedTrue(doctorDtoService.isExistsById(doctorId), 414, "Доктора с таким id нет!");
        return Response.ok(
                talonDtoService.findTalonsByDoctorIdAndTimeBetween(
                        doctorId,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(numberOfDays).with(LocalTime.MAX)));
    }
}
