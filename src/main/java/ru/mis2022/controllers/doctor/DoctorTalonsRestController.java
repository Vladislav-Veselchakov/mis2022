package ru.mis2022.controllers.doctor;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.Talon.DoctorTalonsDto;
import ru.mis2022.models.dto.Talon.TalonDto;
import ru.mis2022.models.dto.talon.converter.TalonConverter;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final PatientService patientService;
    private final TalonService talonService;
    private final TalonConverter talonConverter;

    @PostMapping("/add")
    public Response<List<TalonDto>> addTalons() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctor doctor = doctorService.findByEmail(currentUser.getEmail());
        //todo здесь всегда будет null т.к. по \тому мейлу будет только доктор. это лишнее
        Patient patient = patientService.findByEmail(currentUser.getEmail());

        ApiValidationUtils
                .expectedFalse(talonService.findTalonsCountByIdAndDoctor(numberOfDays, doctor) >= 1, 401,
                        "У доктора есть талоны на данные дни");
        //todo метод persistTalonsForDoctorAndPatient неверный - не надо передавать пациента. по логике метода он всегда должен быть null
        List<Talon> talons = talonService.persistTalonsForDoctorAndPatient(doctor, patient, numberOfDays, numbersOfTalons);

        return Response.ok(talonConverter.toTalonDtoByDoctorId(talons, doctor.getId()));
    }

    @ApiOperation("find current doctor talons on today")
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
        List<DoctorTalonsDto> talonsByCurrentDoctorOnToday = talonService.getTalonsByDoctorIdAndDay(
                doctor.getId(), startDayTime, endDayTime);
        return Response.ok(talonsByCurrentDoctorOnToday);
    }
}
