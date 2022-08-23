package ru.mis2022.controllers.registrar;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.department.DepartmentDto;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.mapper.DepartmentMapper;
import ru.mis2022.models.mapper.DoctorMapper;
import ru.mis2022.models.mapper.MedicalOrganizationMapper;
import ru.mis2022.models.mapper.TalonMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/registrar/schedule")
@PreAuthorize("hasRole('REGISTRAR')")
@RequiredArgsConstructor
public class RegistrarScheduleRestController {

    private final MedicalOrganizationService medicalOrganizationService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final TalonService talonService;
    private final MedicalOrganizationMapper medicalOrganizationMapper;
    private final DepartmentMapper departmentMapper;
    private final DoctorMapper doctorMapper;
    private final TalonMapper talonMapper;

    @ApiOperation("get all medical organizations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список медицинских организаций"),
            @ApiResponse(code = 414, message = "Список медицинских организаций пуст!")
    })
    @GetMapping("/medicalOrganizations")
    public Response<List<MedicalOrganizationDto>> getAllMedicalOrganizations() {
        //todo MedicalOrganizationDtoService сразу получать дто
        List<MedicalOrganization> medicalOrganizations = medicalOrganizationService.findAll();
        return Response.ok(medicalOrganizationMapper.toListDto(medicalOrganizations));
    }

    @ApiOperation("get all departments in medical organization by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список департаментов"),
            @ApiResponse(code = 414, message = "Медицинской организации с таким id нет!"),
            @ApiResponse(code = 415, message = "У медицинской организации нет департаментов!")
    })
    @PostMapping("/departments/{id}")
    public Response<List<DepartmentDto>> getAllDepartmentsByMedicalMedicalOrganizationId(@Valid @PathVariable Long id) {
        ApiValidationUtils
                //todo использовать isExist
                .expectedNotNull(medicalOrganizationService.findMedicalOrganizationById(id),
                        414, "Медицинской организации с таким id нет!");
        //todo DepartmentDtoService сразу получать дто
        List<Department> departments = departmentService.findAllByMedicalOrganizationId(id);
        return Response.ok(departmentMapper.toListDto(departments));
    }

    @ApiOperation("get all doctors in department by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список докторов"),
            @ApiResponse(code = 414, message = "Департамента с таким id нет!"),
            @ApiResponse(code = 415, message = "В департаменте нет докторов!")
    })
    @PostMapping("/doctors/{id}")
    public Response<List<DoctorDto>> getAllDoctorsByDepartmentId(@Valid @PathVariable Long id) {
        ApiValidationUtils
                //todo использовать isExist
                .expectedNotNull(departmentService.findDepartmentById(id),
                        414, "Департамента с таким id нет!");
        //todo DoctorDtoService сразу получать дто
        List<Doctor> doctors = doctorService.findAllByDepartmentId(id);
        return Response.ok(doctorMapper.toListDto(doctors));
    }

    @ApiOperation("get all talons by doctor id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список талонов"),
            @ApiResponse(code = 414, message = "Доктора с таким id нет!"),
            @ApiResponse(code = 415, message = "У доктора нет талонов!")
    })
    @PostMapping("/talons/{id}")
    public Response<List<TalonDto>> getAllTalonsByDoctorId(@Valid @PathVariable Long id) {
        ApiValidationUtils
                //todo использовать isExist
                .expectedNotNull(doctorService.findByDoctorId(id),
                        414, "Доктора с таким id нет!");
        //todo TalonDtoService сразу получать дто
        List<Talon> talons = talonService.findAllByDoctorId(id);
        return Response.ok(talonMapper.toListDto(talons));
    }
}
