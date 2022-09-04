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
import ru.mis2022.models.dto.organization.converter.MedicalOrganizationDtoConverter;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.MedicalOrganizationDtoService;
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
    private final MedicalOrganizationDtoService medicalOrganizationDtoService;
    private final MedicalOrganizationDtoConverter medicalOrganizationDtoConverter;

    @ApiOperation("Кадровик получает все медицинские организации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все организации"),
    })
    @GetMapping("/medicalOrganizations")
    public Response<List<MedicalOrganizationDto>> medicalOrganizationList() {
        return Response.ok(medicalOrganizationDtoService.findAll());
    }

    @ApiOperation("Кадровик создает медицинскую организацию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская организация создана"),
            @ApiResponse(code = 412, message = "Такое имя медицинской организации уже используется!")
    })
    @Validated(OnCreate.class)
    @PostMapping("/createMedicalOrganizations")
    public Response<MedicalOrganizationDto> saveMedicalOrganization(@Valid @RequestBody
                                                                    MedicalOrganizationDto medicalOrganizationDto) {
        ApiValidationUtils
                .expectedFalse(medicalOrganizationService.isExistByName(medicalOrganizationDto.getName()),
                        412, "Такое имя медицинской организации уже используется!");
        MedicalOrganization medicalOrganization =
                medicalOrganizationService.save(
                        medicalOrganizationDtoConverter.toEntity(medicalOrganizationDto));
        return Response.ok(medicalOrganizationDtoConverter.toDto(medicalOrganization));
    }

    @ApiOperation("Кадровик обновляет существующую медицинскую организацию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Медицинская организация обновлена"),
            @ApiResponse(code = 410, message = "По переданному id медицинская организация не найдена."),
    })
    @Validated(OnUpdate.class)
    @PutMapping("/updateMedicalOrganizations")
    public Response<MedicalOrganizationDto> updateMedicalOrganization(@Valid @RequestBody
                                                                      MedicalOrganizationDto medicalOrganizationDto) {
        ApiValidationUtils
                .expectedTrue(medicalOrganizationService.isExistById(medicalOrganizationDto.getId()),
                        410, "По переданному id медицинская организация не найдена.");
        MedicalOrganization medicalOrganization =
                medicalOrganizationService.save(medicalOrganizationDtoConverter.toEntity(medicalOrganizationDto));
        return Response.ok(medicalOrganizationDtoConverter.toDto(medicalOrganization));

    }

    @ApiOperation("Кадровик удаляет медицинскую организацию")
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
