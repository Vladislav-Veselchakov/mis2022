package ru.mis2022.controllers.patient;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mis2022.models.dto.DepartmentDto.DepartmentDto;
import ru.mis2022.models.dto.medicalOrganization.MedicalOrganizationDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.mapper.DepartmentMapper;
import ru.mis2022.models.mapper.MedicalOrganizationMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.PatientDtoService;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping(value ="/api/patient")
public class PatientSheduleRestController {

    private final PatientDtoService patientDtoService;
    private final DepartmentService departmentService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final MedicalOrganizationMapper medicalOrganizationMapper;
    private final DepartmentMapper departmentMapper;

    @ApiOperation("get all medical organizations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список медицинских организаций"),
            @ApiResponse(code = 414, message = "Список медицинских организаций пуст")
    })
    @GetMapping(value = "/medicalOrganizations")
    public Response<List<MedicalOrganizationDto>> getAllMedicalOrganization() {
        List<MedicalOrganization> medicalOrganizations = medicalOrganizationService.findAll();
        //todo не надо кидать эксепшн. надо возвращать пустую коллекцию
        ApiValidationUtils
                .expectedFalse(medicalOrganizations.size() == 0,
                        414,
                        "Список медицинских организаций пуст");
        return Response.ok(medicalOrganizationMapper.toListDto(medicalOrganizations));
    }

    @ApiOperation("get all departments by medical organization id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список департаментов"),
            @ApiResponse(code = 414, message = "Медицинской организации с таким id нет"),
            @ApiResponse(code = 415, message = "У медицинской организации нет департаментов")
    })
    @GetMapping(value = "/medicalOrganization/{medOrgId}/getAllDepartments")
    public Response<List<DepartmentDto>> getAllDepartmentsByMedicalMedicalOrganizationId(
            @Valid @PathVariable Long medOrgId) {
        ApiValidationUtils
                .expectedNotNull(medicalOrganizationService.existById(medOrgId),
                        414, "Медицинской организации с таким id нет");
        List<Department> departments = departmentService.findAllByMedicalOrganization_Id(medOrgId);
        //todo не надо кидать эксепшн. надо возвращать пустую коллекцию
        ApiValidationUtils
                .expectedFalse(departments.size()==0,
                        415, "У медицинской организации нет департаментов!");
        return Response.ok(departmentMapper.toListDto(departments));
    }
}
