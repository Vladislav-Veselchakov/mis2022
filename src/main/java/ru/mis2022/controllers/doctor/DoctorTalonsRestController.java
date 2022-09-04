package ru.mis2022.controllers.doctor;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.dto.talon.TalonByDay;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.dto.talon.converter.TalonDtoConverter;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.TalonDtoService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
@RequestMapping("/api/doctor/talon")
public class DoctorTalonsRestController {
    @Value("${mis.property.doctorSchedule}")
    private Integer numberOfDays;
    @Value("${mis.property.talon}")
    private Integer numbersOfTalons;

    private final DoctorService doctorService;
    private final TalonService talonService;
    private final TalonDtoConverter converter;
    private final TalonDtoService talonDtoService;

    @ApiOperation("Доктор создает себе пустые талоны на диапазон времени")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Талоны созданы"),
            @ApiResponse(code = 401, message = "У доктора есть талоны на данные дни"),
    })
    @PostMapping("/add")
    public Response<List<TalonDto>> addTalons() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctor doctor = doctorService.findByEmail(currentUser.getEmail());

        ApiValidationUtils
                .expectedFalse(talonService.findTalonsCountByIdAndDoctor(numberOfDays, doctor) >= 1, 401,
                        "У доктора есть талоны на данные дни");

        List<Talon> talons = talonService.persistTalonsForDoctor(doctor, numberOfDays, numbersOfTalons);

        return Response.ok(converter.toTalonDtoByDoctorId(talons, doctor.getId()));
    }

    @ApiOperation("Доктор получает все свои талоны")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список талонов"),
            @ApiResponse(code = 414, message = "Доктора с таким id нет!"),
    })
    //todo list1 удалить id доктора из урл, доктор должен находить только свои талоны
    @GetMapping("/get/group/{doctorId}")
    public Response<List<TalonByDay>> getAllTalonsByDoctorId(@PathVariable Long doctorId) {
        ApiValidationUtils
                .expectedTrue(doctorService.isExistsById(doctorId),
                        414, "Доктора с таким id нет!");
        return Response.ok(converter.groupByDay(
                talonDtoService.findAllByDoctorId(doctorId).orElse(Collections.emptyList())));
    }

    @ApiOperation("Доктор получает свои талоны на сегодня")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список талонов доктора на сегодня"),
    })
    @GetMapping("/onToday")
    public Response <List<DoctorTalonsDto>> talonsOnToday() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Doctor doctor = doctorService.findByEmail(currentUser.getEmail());
        LocalDateTime startDayTime = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endDayTime = LocalDateTime.now().with(LocalTime.MAX);
        return Response.ok(talonDtoService.getTalonsByDoctorIdAndDay(doctor.getId(), startDayTime, endDayTime));
    }
}
