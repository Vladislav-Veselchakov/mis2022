package ru.mis2022.controllers.economist;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.disease.DiseaseConverter;
import ru.mis2022.models.dto.disease.DiseaseDto;
import ru.mis2022.models.entity.Disease;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.DiseaseService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist/disease")
public class EconomistDiseaseRestController {

    private final DiseaseService diseaseService;
    private final DiseaseConverter converter;


    @GetMapping("/getAll")
    public Response<List<DiseaseDto>> getAllDisease() {
        return Response.ok(diseaseService.findAllDiseaseDto());
    }

    @PostMapping("/create")
    public Response<DiseaseDto> persistDisease(@RequestBody DiseaseDto diseaseDto) {
        ApiValidationUtils
                .expectedFalse(diseaseService.isExistByIdentifier(diseaseDto.identifier()), 410,
                        "Заболевание с данным идентификатором уже существует");
        return Response.ok(converter.toDiseaseDto(
                diseaseService.save(Disease.builder()
                        .identifier(diseaseDto.identifier())
                        .name(diseaseDto.name())
                        .build())));
    }

    @DeleteMapping("/delete/{diseaseId}")
    public Response<Void> deleteDiseaseById(@PathVariable Long diseaseId) {
        ApiValidationUtils
                .expectedTrue(diseaseService.isExistById(diseaseId), 411,
                        "Заболевание с переданным id не существует");
        diseaseService.deleteById(diseaseId);
        return Response.ok();
    }
}
