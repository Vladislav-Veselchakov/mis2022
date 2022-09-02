package ru.mis2022.controllers.patient;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientTalonsRestControllerIT extends ContextIT {

    @Autowired
    RoleService roleService;
    @Autowired
    PatientService patientService;
    @Autowired
    TalonService talonService;
    @Autowired
    DoctorService doctorService;

    Role initRole(String name) {
        return roleService.save(Role.builder()
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

    Patient initPatient(Role role, String email) {
        return patientService.persist(new Patient(
                email,
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

    Talon initTalon(LocalDateTime time, Doctor doctor, Patient patient) {
        return new Talon(time, doctor, patient);
    }

    @Test
    public void getAllTalonsPatient() throws Exception {

        Role role = initRole("PATIENT");
        Role role1 = initRole("DOCTOR");
        Patient patient = initPatient(role, "patient1@email.com");
        Doctor doctor = initDoctor(role1, null, null, "doctor1@email.com");
        talonService.persistTalonsForDoctor(doctor, 14, 4);

        // Берем все получившиеся талонры и в след. строке заполнряем их пациентами:
        List<DoctorTalonsDto> doc4Talons = talonService.getTalonsByDoctorIdAndDay(doctor.getId(),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now().plusDays(14), LocalTime.MAX));
        // заполняем все талоны пациентом:
        doc4Talons.stream()
                .map(doctorTalonsDto-> {
                    Talon talon = talonService.findTalonById(doctorTalonsDto.id());
                    talon.setPatient(patient);
                    return talon;
                })
                .forEach(talonService::save);


        accessToken = tokenUtil.obtainNewAccessToken(patient.getEmail(), "1", mockMvc);

        //Вывод всех талонов где есть запись пациента
        mockMvc.perform(get("/api/patient/talons/{patientId}", patient.getId())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].patientId", Is.is(patient.getId().intValue())));
        //      .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));



        //Пациента с таким id нет
        mockMvc.perform(get("/api/patient/talons/{patientId}", 100)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(402)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Пациента с таким id нет!")));
    }

    @Test
    public void cancelRecordTalonsTest() throws Exception {

        Role role = initRole("PATIENT");
        Role role1 = initRole("DOCTOR");
        Patient patient = initPatient(role, "patient1test@email.com");
        Patient patient2 = initPatient(role, "patient2test@email.com");
        Doctor doctor = initDoctor(role1, null, null, "doctor1test@email.com");
        talonService.persistTalonsForDoctor(doctor,14, 4);
        Talon talon = initTalon(null, doctor, patient);
        Talon talon2 = initTalon(LocalDateTime.now(), doctor, patient2);
        talon = talonService.save(talon);
        talon2 = talonService.save(talon2);

        accessToken = tokenUtil.obtainNewAccessToken(patient.getEmail(), "1", mockMvc);

        if (talon.getPatient()==null || talon2.getPatient()==null || talon.getId()==null || talon2.getId() ==null) {
            throw new Exception("Неправильно созданы талоны для теста");
        }

        //Проверка на несуществующий талон
        mockMvc.perform(patch("/api/patient/talons/{talonId}", 150000)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(402)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Талона с таким id нет!")));
//              .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //Пациент пытается удалить чужую запись
        mockMvc.perform(patch("/api/patient/talons/{talonId}", talon2.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(403)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Пациент не записан по этому талону")));
//               .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //эндпоинт отработал успешно
        mockMvc.perform(patch("/api/patient/talons/{talonId}", talon.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

//        Запись на талон успешно удалена
        if (talon.getPatient()!=null || talon2.getPatient()==null) {
            throw new Exception("Запись по талону не удалена");
        }
    }
}
