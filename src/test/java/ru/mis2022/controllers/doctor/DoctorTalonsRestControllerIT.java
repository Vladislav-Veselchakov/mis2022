package ru.mis2022.controllers.doctor;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.entity.*;
import ru.mis2022.service.entity.*;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.mis2022.utils.DateFormatter.DATE_FORMATTER;
import static ru.mis2022.utils.DateFormatter.DATE_TIME_FORMATTER;


public class DoctorTalonsRestControllerIT extends ContextIT {

    @Autowired
    TalonService talonService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PatientService patientService;

    @Value("${mis.property.doctorSchedule}")
    private Integer numberOfDays;

    @Value("${mis.property.talon}")
    private Integer numbersOfTalons;

    Role initRole(String name) {
        return roleService.save(Role.builder()
                .name(name)
                .build());
    }

    Department initDepartment(String name) {
        return departmentService.save(Department.builder()
                .name(name)
                .build());
    }

    Doctor initDoctor(Role role, Department department, PersonalHistory personalHistory, String email) {
        return doctorService.persist(new Doctor(
                email,
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role,
                department
        ));
    }
    Patient initPatient(Role role) {
        return patientService.persist(new Patient(
                "patient1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role,
                "passport",
                "polis",
                "snils",
                "address"));
    }

    Talon initTalon(Talon talon) {
        return talonService.save(talon);
    }

    private String formatDate(LocalDate date, int hour) {
        LocalDateTime time = LocalDateTime.of(date, LocalTime.of(8, 0).plusHours(hour));
        return time.format(DATE_TIME_FORMATTER);
    }

    String todayTimeTalon(int hour) {
        return LocalDateTime.now().with(LocalTime.of(hour,0))
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Test
    public void addTalonTest() throws Exception {

        Role role = initRole("DOCTOR");
        Role role1 = initRole("PATIENT");
        Department department = initDepartment("Therapy");
        Doctor doctor1 = initDoctor(role, department, null, "doctor@email.com");
        Patient patient = initPatient(role1);

        LocalDate date = LocalDate.now();

        LocalDateTime time2 = LocalDateTime.of(LocalDate.now().plusDays(13), LocalTime.of(8, 0).plusHours(3));
        String foramttedString2 = time2.format(DATE_TIME_FORMATTER);

        accessToken = tokenUtil.obtainNewAccessToken(doctor1.getEmail(), "1", mockMvc);

        mockMvc.perform(post("/api/doctor/talon/add")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[0].time", Is.is(formatDate(date, 0))))
                .andExpect(jsonPath("$.data[0].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[1].time", Is.is(formatDate(date, 1))))
                .andExpect(jsonPath("$.data[1].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[2].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[2].time", Is.is(formatDate(date, 2))))
                .andExpect(jsonPath("$.data[2].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[3].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[3].time", Is.is(formatDate(date, 3))))
                .andExpect(jsonPath("$.data[3].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[55].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[55].time", Is.is(foramttedString2)))
                .andExpect(jsonPath("$.data[55].doctorId", Is.is(doctor1.getId().intValue())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));


        mockMvc.perform(post("/api/doctor/talon/add")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(401)))
                .andExpect(jsonPath("$.text", Is.is("У доктора есть талоны на данные дни")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    // todo на стыке месяцов тест не работет. Например, если сегодня 28.08 то далее к этой дате добавляется 4 дня и
    // получается 02.09. Тест ожидает первый талон с датой 28.08, а получает 02.09, т.к. в классе
    // TalonDtoConverter.groupByDay(..) идет сортировка по строке
    public void getAllTalonsByDoctorIdTest() throws Exception {
        Role role = initRole("DOCTOR");
        Role role1 = initRole("PATIENT");
        Department department = initDepartment("Therapy");
        Doctor doctor1 = initDoctor(role, department, null, "doctor1@email.com");
        Doctor doctor2 = initDoctor(role, department, null, "doctor2@email.com");
        Patient patient = initPatient(role1);
        Talon talon1 = initTalon(new Talon(LocalDateTime.now(), doctor1, null));
        Talon talon2 = initTalon(new Talon(LocalDateTime.now(), doctor1, patient));
        Talon talon3 = initTalon(new Talon(LocalDateTime.now(), doctor1, null));
        Talon talon4 = initTalon(new Talon(LocalDateTime.now().plusDays(2), doctor1, patient));
        Talon talon5 = initTalon(new Talon(LocalDateTime.now().plusDays(4), doctor1, null));
        LocalDate date = LocalDate.now();
        String formatDate = date.format(DATE_FORMATTER);

        accessToken = tokenUtil.obtainNewAccessToken(doctor1.getEmail(), "1", mockMvc);

        // У ДОКТОРА 5 ТАЛОНОВ ИЗ КОТОРЫХ 2 ЗАНЯТО
        mockMvc.perform(get("/api/doctor/talon/get/group/{doctorId}", doctor1.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].date", Is.is(formatDate)))
                .andExpect(jsonPath("$.data[0].talonsDto[0].id", Is.is(talon1.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[0].time", Is.is(talon1.getTime().format(DATE_TIME_FORMATTER))))
                .andExpect(jsonPath("$.data[0].talonsDto[0].doctorId", Is.is(doctor1.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[0].patientId", Is.is(Matchers.nullValue())))
        //      .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

                .andExpect(jsonPath("$.data[0].talonsDto[1].id", Is.is(talon2.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[1].time", Is.is(talon2.getTime().format(DATE_TIME_FORMATTER))))
                .andExpect(jsonPath("$.data[0].talonsDto[1].doctorId", Is.is(doctor1.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[1].patientId", Is.is(patient.getId().intValue())))

                .andExpect(jsonPath("$.data[0].talonsDto[2].id", Is.is(talon3.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[2].time", Is.is(talon3.getTime().format(DATE_TIME_FORMATTER))))
                .andExpect(jsonPath("$.data[0].talonsDto[2].doctorId", Is.is(doctor1.getId().intValue())))
                .andExpect(jsonPath("$.data[0].talonsDto[2].patientId", Is.is(Matchers.nullValue())))

                .andExpect(jsonPath("$.data[1].talonsDto[0].id", Is.is(talon4.getId().intValue())))
                .andExpect(jsonPath("$.data[1].talonsDto[0].time", Is.is(talon4.getTime().format(DATE_TIME_FORMATTER))))
                .andExpect(jsonPath("$.data[1].talonsDto[0].doctorId", Is.is(doctor1.getId().intValue())))
                .andExpect(jsonPath("$.data[1].talonsDto[0].patientId", Is.is(patient.getId().intValue())))

                .andExpect(jsonPath("$.data[2].talonsDto[0].id", Is.is(talon5.getId().intValue())))
                .andExpect(jsonPath("$.data[2].talonsDto[0].time", Is.is(talon5.getTime().format(DATE_TIME_FORMATTER))))
                .andExpect(jsonPath("$.data[2].talonsDto[0].doctorId", Is.is(doctor1.getId().intValue())))
                .andExpect(jsonPath("$.data[2].talonsDto[0].patientId", Is.is(Matchers.nullValue())));

        // У ДОКТОРА НЕТ ТАЛОНОВ
        mockMvc.perform(get("/api/doctor/talon/get/group/{doctorId}", doctor2.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        // ДОКТОРА С ТАКИМ ID НЕТ
        mockMvc.perform(get("/api/doctor/talon/get/group/{doctorId}", 888888)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id нет!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        }

    @Test
    public void onTodayTalonsTest() throws Exception {

        Role role = initRole("DOCTOR");
        Role role1 = initRole("PATIENT");
        Department department = initDepartment("Therapy");
        Doctor doctor = initDoctor(role, department, null, "doctor@email.com");
        Patient patient = initPatient(role1);
        talonService.persistTalonsForDoctor(doctor, numberOfDays, numbersOfTalons);

        // Берем получившиеся талоны (чтобы дальше заполнить пациентом)
        List<DoctorTalonsDto> doc4Talons = talonService.getTalonsByDoctorIdAndDay(
                doctor.getId(),
                LocalDateTime.of(LocalDate.now(),
                LocalTime.MIN),
                LocalDateTime.of(LocalDate.now().plusDays(numberOfDays), LocalTime.MAX));

        // Заполняем все свободные талоны пациентом:
        doc4Talons.stream()
                .map(doctorTalonsDto-> {
                    Talon talon = talonService.findTalonById(doctorTalonsDto.id());
                    talon.setPatient(patient);
                    return talon;
                })
                .forEach(talonService::save);

        accessToken = tokenUtil.obtainNewAccessToken(doctor.getEmail(), "1", mockMvc);

        mockMvc.perform(get("/api/doctor/talon/onToday")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[0].time", Is.is(todayTimeTalon(8))))
                .andExpect(jsonPath("$.data[0].patientId", Is.is(patient.getId().intValue())))

                .andExpect(jsonPath("$.data[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[1].time", Is.is(todayTimeTalon(9))))
                .andExpect(jsonPath("$.data[1].patientId", Is.is(patient.getId().intValue())))

                .andExpect(jsonPath("$.data[2].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[2].time", Is.is(todayTimeTalon(10))))
                .andExpect(jsonPath("$.data[2].patientId", Is.is(patient.getId().intValue())))

                .andExpect(jsonPath("$.data[3].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[3].time", Is.is(todayTimeTalon(11))))
                .andExpect(jsonPath("$.data[3].patientId", Is.is(patient.getId().intValue())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}
