package ru.mis2022.controllers.doctor;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.aspectj.runtime.internal.Conversions.intValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorPatientRestControllerIT extends ContextIT {

    @Autowired
    DoctorService doctorService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PatientService patientService;

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

    Patient initPatient(String email, String firstName, String lastName, String surname, Role role, String passport, String polis, String snils) {
        return patientService.persist(new Patient(
                email,
                String.valueOf("1"),
                firstName,
                lastName,
                surname,
                LocalDate.now().minusYears(20),
                role,
                passport,
                polis,
                snils,
                null
        ));
    }

    @Test
    public void getPatientByFullNameTest () throws Exception {
        Role role = initRole("DOCTOR");
        Role role1 = initRole("PATIENT");
        Department department = initDepartment("Therapy");
        Doctor doctor1 = initDoctor(role, department, null, "doctor1@email.com");
        Patient patient1 = initPatient("email1@rt.ru","Alexandr", "Safronov", "Sergeevich", role1,"2222 878190","2349581209685472","567-476-439 85");
        Patient patient2 = initPatient("email2@rt.ru","Igor", "Livanov", null, role1,"3407 878190","2349581209685472","567-476-43 98");
        Patient patient3 = initPatient("email3@rt.ru","Oleg", "Karimov", "Sergeevich", role1,"5671 878190","2349581209685472","567-476-434 56");
        Patient patient4 = initPatient("email4@rt.ru","Sergey", "Petrov", "Sergeevich", role1,"1298 878190","2349581209685472","567-476-401 93");
        Patient patient5 = initPatient("email5@rt.ru","Petr", "Krasov", "Sergeevich", role1,"1298 878190","2349581209685472","567-476-401 93");
        Patient patient6 = initPatient("email6@rt.ru","Vladimir", "Ivanov", null,  role1,"7304 878190","2349581209685472","567-476-439 80");

        accessToken = tokenUtil.obtainNewAccessToken(doctor1.getEmail(), "1", mockMvc);

        String fullName = "pet";
        mockMvc.perform(get("/api/doctor/patient/stringPattern")
                .header("Authorization", accessToken)
                .param("stringPattern", fullName)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                //PETR KRASOV
                .andExpect(jsonPath("$.data[0].id", Is.is(intValue(patient5.getId()))))
                //SERGEY PETROV
                .andExpect(jsonPath("$.data[1].id", Is.is(intValue(patient4.getId()))))
                .andExpect(jsonPath("$.data[0].firstName", Is.is(patient5.getFirstName())))
                .andExpect(jsonPath("$.data[0].lastName", Is.is(patient5.getLastName())))
                .andExpect(jsonPath("$.data[0].surName", Is.is(patient5.getSurname())))
                .andExpect(jsonPath("$.data[0].birthday", Is.is(patient5.getBirthday().format(DateTimeFormatter.ofPattern( "dd.MM.yyyy" )))))//format(dateFormat = "dd.MM.yyyy"))))
                .andExpect(jsonPath("$.data[0].passport", Is.is(patient5.getPassport().replaceAll("\\s+","").substring(6))))
                .andExpect(jsonPath("$.data[0].polis", Is.is(patient5.getPolis().substring(12))))
                .andExpect(jsonPath("$.data[0].snils", Is.is(patient5.getSnils().replaceAll("[^A-Za-z�-��-�0-9]", "").substring(7))));
//              .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        String fullName1 = "saf and";
        mockMvc.perform(get("/api/doctor/patient/stringPattern")
                        .header("Authorization", accessToken)
                        .param("stringPattern", fullName1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                //ALEXANDR SAFRONOV
                .andExpect(jsonPath("$.data[0].id", Is.is(intValue(patient1.getId()))))
                .andExpect(jsonPath("$.data[0].firstName", Is.is(patient1.getFirstName())))
                .andExpect(jsonPath("$.data[0].lastName", Is.is(patient1.getLastName())))
                .andExpect(jsonPath("$.data[0].surName", Is.is(patient1.getSurname())))
                .andExpect(jsonPath("$.data[0].birthday", Is.is(patient1.getBirthday().format(DateTimeFormatter.ofPattern( "dd.MM.yyyy" )))))
                .andExpect(jsonPath("$.data[0].passport", Is.is(patient1.getPassport().replaceAll("\\s+","").substring(6))))
                .andExpect(jsonPath("$.data[0].polis", Is.is(patient1.getPolis().substring(12))))
                .andExpect(jsonPath("$.data[0].snils", Is.is(patient1.getSnils().replaceAll("[^A-Za-z�-��-�0-9]", "").substring(7))));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        String fullName2 = "nov gey";
        mockMvc.perform(get("/api/doctor/patient/stringPattern")
                        .header("Authorization", accessToken)
                        .param("stringPattern", fullName2)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
//              .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        String fullName3 = "biz";
        mockMvc.perform(get("/api/doctor/patient/stringPattern")
                        .header("Authorization", accessToken)
                        .param("stringPattern", fullName3)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
    }
}
