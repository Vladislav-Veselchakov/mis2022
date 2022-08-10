package ru.mis2022.controllers.patient;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientSheduleRestControllerIT extends ContextIT {

    @Autowired
    RoleService roleService;
    @Autowired
    PatientService patientService;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    MedicalOrganizationService medicalOrganizationService;

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
}


