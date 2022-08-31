package ru.mis2022.controllers.doctor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.doctor.ChiefReportDto;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.ChiefReportService;

import java.time.LocalDate;
import java.util.List;

import static ru.mis2022.utils.DateFormatter.DATE_FORMATTER;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('CHIEF_DOCTOR')")
@RequestMapping("/api/chief/doctor")
@Api(tags = "Workload employees report for cheaf-doctor")
public class ChiefDoctorReportRestController {
    private final ChiefReportService chiefReportService;

    @ApiOperation("Отчет о загруженности докторов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список талонов")
    })
    @GetMapping("/workload_employees_report")
    public Response<List<ChiefReportDto>> getWorkloadReport(@RequestParam(name = "dateStart") String pDateStart,
                                                            @RequestParam(name = "dateEnd") String pDateEnd) {
        return Response.ok(
                chiefReportService.getReport(
                        LocalDate.parse(pDateStart, DATE_FORMATTER),
                        LocalDate.parse(pDateEnd, DATE_FORMATTER)));
    }

}
