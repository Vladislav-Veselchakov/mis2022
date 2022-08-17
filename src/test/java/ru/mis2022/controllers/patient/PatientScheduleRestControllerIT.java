package ru.mis2022.controllers.patient;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientScheduleRestControllerIT extends ContextIT {

    @Value("${mis.property.doctorSchedule}")
    private Integer numberOfDays;
    @Value("${mis.property.talon}")
    private Integer numbersOfTalons;

    @Autowired
    RoleService roleService;
    @Autowired
    PatientService patientService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    MedicalOrganizationService medicalOrganizationService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    TalonService talonService;

    MedicalOrganization initMedicalOrganization(String name, String address) {
        return medicalOrganizationService.persist(MedicalOrganization.builder()
                .name(name)
                .address(address)
                .build());
    }

    Role initRole(String name) {
        return roleService.persist(Role.builder()
                .name(name)
                .build());
    }

    Department initDepartment(String name, MedicalOrganization medicalOrganization) {
        return departmentService.persist(Department.builder()
                .name(name)
                .medicalOrganization(medicalOrganization)
                .build());
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
                "Passport: 1234 567890",
                "Polis 770000 123456789",
                "Snils 123 456789",
                "Moscow, Mira st. 12-4-35"
        ));
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

    @Test
    public void getAllMedicalOrganizationsTest() throws Exception {
        Role rolePatient = initRole("PATIENT");
        Patient patient = initPatient(rolePatient);

        accessToken = tokenUtil.obtainNewAccessToken(patient.getEmail(), "1", mockMvc);

        //Пустой список медицинских организаций
        mockMvc.perform(get("/api/patient/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Список медицинских организаций пуст")));

       initMedicalOrganization("City Hospital", "Moscow, Pravda street, 30");

       initMedicalOrganization("Hospital №1", "St. Peterburg, Lenina avenue, 3");

       initMedicalOrganization("City Clinic Hospital", "Saratov, Grin street, 25");

        //Вывод списка мед организаций
        mockMvc.perform(get("/api/patient/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].name", Is.is("City Hospital")))
                .andExpect(jsonPath("$.data[0].address", Is.is("Moscow, Pravda street, 30")))

                .andExpect(jsonPath("$.data.[1].name", Is.is("Hospital №1")))
                .andExpect(jsonPath("$.data.[1].address", Is.is("St. Peterburg, Lenina avenue, 3")))

                .andExpect(jsonPath("$.data[2].name", Is.is("City Clinic Hospital")))
                .andExpect(jsonPath("$.data[2].address", Is.is("Saratov, Grin street, 25")));
    }

    @Test
    void FetchAllDepartmentsOfMedicalOrganization() throws Exception {
        Role rolePatient = initRole("PATIENT");
        Patient patient = initPatient(rolePatient);

        accessToken = tokenUtil.obtainNewAccessToken(patient.getEmail(), "1", mockMvc);

        mockMvc.perform(get("/api/patient/medicalOrganization/{medOrgId}/getAllDepartments", 100)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Медицинской организации с таким id нет")));


        MedicalOrganization medicalOrganization = initMedicalOrganization("City Hospital", "Moscow, Pravda street, 30");

        mockMvc.perform(get("/api/patient/medicalOrganization/{medOrgId}/getAllDepartments", medicalOrganization.getId())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(415)))
                .andExpect(jsonPath("$.text", Is.is(
                        "У медицинской организации нет департаментов!")));


        initDepartment("Therapy", medicalOrganization);

        initDepartment("Surgery", medicalOrganization);

        initDepartment("Pediatrics", medicalOrganization);

        mockMvc.perform(get("/api/patient/medicalOrganization/{medOrgId}/getAllDepartments", medicalOrganization.getId())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.[0].name", Is.is("Therapy")))
                .andExpect(jsonPath("$.data[1].name", Is.is("Surgery")))
                .andExpect(jsonPath("$.data[2].name", Is.is("Pediatrics")));
    }

    @Test
    void getAllDoctorsByDepartmentsIdTest() throws Exception {
        Department department = initDepartment("Pediatrics", null);
        Department department2 = initDepartment("Surgery", null);

        Role rolePatient = initRole("PATIENT");
        Role roleDoctor = initRole("DOCTOR");
        Patient patient = initPatient(rolePatient);

        Doctor doctor = initDoctor(roleDoctor, department, null, "doctor1@email.com");
        Doctor doctor2 = initDoctor(roleDoctor, department2, null, "doctor2@email.com");
        Doctor doctor3 = initDoctor(roleDoctor, department, null, "doctor3@email.com");
        Doctor doctor4 = initDoctor(roleDoctor, department, null, "doctor4@email.com");

        talonService.persistTalonsForDoctorAndPatient(doctor, null, numberOfDays, numbersOfTalons);
        talonService.persistTalonsForDoctorAndPatient(doctor2, patient, numberOfDays, numbersOfTalons);
        talonService.persistTalonsForDoctorAndPatient(doctor3, null, numberOfDays, numbersOfTalons);
        talonService.persistTalonsForDoctorAndPatient(doctor4, patient, numberOfDays, numbersOfTalons);


        accessToken = tokenUtil.obtainNewAccessToken(patient.getEmail(), "1", mockMvc);

        //Такого департамента нет
        mockMvc.perform(get("/api/patient/departments/{departmentId}/getAllDoctors", 88888)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is("Департамента с таким id нет!")));


        //Вывод списка всех докторов с пустыми талонами
        //Проверка , что не попадает в список доктор с занятыми талонами
        mockMvc.perform(get("/api/patient/departments/{departmentId}/getAllDoctors", department.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(2)))

                .andExpect(jsonPath("$.data[0].role", Is.is("DOCTOR")))
                .andExpect(jsonPath("$.data[0].lastName", Is.is(doctor.getLastName())))
                .andExpect(jsonPath("$.data[0].firstName", Is.is(doctor.getFirstName())))
                .andExpect(jsonPath("$.data[0].department", Is.is("Pediatrics")))
                .andExpect(jsonPath("$.data[0].birthday", Matchers.notNullValue()))

                .andExpect(jsonPath("$.data[0].role", Is.is("DOCTOR")))
                .andExpect(jsonPath("$.data[0].lastName", Is.is(doctor3.getLastName())))
                .andExpect(jsonPath("$.data[0].firstName", Is.is(doctor3.getFirstName())))
                .andExpect(jsonPath("$.data[0].department", Is.is("Pediatrics")))
                .andExpect(jsonPath("$.data[0].birthday", Matchers.notNullValue()));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));


        //В этом департаменте нет докторов с пустыми талонами
        mockMvc.perform(get("/api/patient/departments/{departmentId}/getAllDoctors", department2.getId())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}


