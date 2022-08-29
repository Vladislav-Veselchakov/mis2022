package ru.mis2022.controllers.hrManager;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.mapper.MedicalOrganizationMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.utils.validation.ApiValidationUtils;
import ru.mis2022.utils.validation.OnCreate;
import ru.mis2022.utils.validation.OnUpdate;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/hr_manager")
public class HrManagerOrganizationRestController {
    private final MedicalOrganizationService medicalOrganizationService;
    private final MedicalOrganizationMapper medicalOrganizationMapper;

    @ApiOperation("get all medical organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все организации"),
    })
    @GetMapping("/medicalOrganizations")
    public Response<List<MedicalOrganizationDto>> medicalOrganizationList() {
        //todo list2 MedicalOrganizationDtoService получать сразу дто
        List<MedicalOrganization> medicalOrganizations = medicalOrganizationService.findAll();
        return Response.ok(medicalOrganizationMapper.toListDto(medicalOrganizations));
    }


    @ApiOperation("creatе medical organization ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская организация создана"),
            @ApiResponse(code = 412, message = "Такое имя медицинской организации уже используется!")
    })
    @PostMapping("/createMedicalOrganizations")
    @Validated(OnCreate.class)
    public Response<MedicalOrganizationDto> saveMedicalOrganization(@Valid @RequestBody
                                                                    MedicalOrganizationDto medicalOrganizationDto) {
        ApiValidationUtils
                .expectedFalse(medicalOrganizationService.isExistByName(medicalOrganizationDto.getName()),
                        412, "Такое имя медицинской организации уже используется!");
        MedicalOrganization medicalOrganization =
                medicalOrganizationService.save(
                        medicalOrganizationMapper.toEntity(
                                medicalOrganizationDto));
        return Response.ok(medicalOrganizationMapper.toDto(medicalOrganization));
    }


    @ApiOperation("update medical organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская организация обновлена"),
            @ApiResponse(code = 410, message = "По переданному id медицинская организация не найдена."),
    })
    @PutMapping("/updateMedicalOrganizations")
    @Validated(OnUpdate.class)
    public Response<MedicalOrganizationDto> updateMedicalOrganization(@Valid @RequestBody
                                                                      MedicalOrganizationDto medicalOrganizationDto) {
        ApiValidationUtils
                .expectedTrue(medicalOrganizationService.isExistById(medicalOrganizationDto.getId()),
                        410, "По переданному id медицинская организация не найдена.");
        MedicalOrganization medicalOrganization =
                medicalOrganizationService.save(medicalOrganizationMapper
                        .toEntity(medicalOrganizationDto));
        return Response.ok(medicalOrganizationMapper.toDto(medicalOrganization));
    }

    @ApiOperation("delete medical organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская организация удалена"),
            @ApiResponse(code = 414, message = "Медицинской организации с таким id нет!")
    })
    @DeleteMapping("/deleteMedicalOrganization/{id}")
    public Response<Void> deleteMedicalOrganization(@PathVariable Long id) {
        ApiValidationUtils
                .expectedTrue(medicalOrganizationService.isExistById(id),
                        414, "Медицинской организации с таким id нет!");
        medicalOrganizationService.delete(id);
        return Response.ok();
    }

}
