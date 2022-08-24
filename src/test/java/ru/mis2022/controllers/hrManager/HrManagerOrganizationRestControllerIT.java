package ru.mis2022.controllers.hrManager;


import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.dto.organization.MedicalOrganizationDto;
import ru.mis2022.models.entity.HrManager;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.HrManagerService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HrManagerOrganizationRestControllerIT extends ContextIT {
    @Autowired
    HrManagerService hrManagerService;
    @Autowired
    RoleService roleService;
    @Autowired
    MedicalOrganizationService medicalOrganizationService;

    Role initRole(String name) {
        return roleService.save(Role.builder()
                .name(name)
                .build());
    }

    HrManager initHrManager(Role role) {
        return hrManagerService.persist(new HrManager(
                "hrManager1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surName",
                LocalDate.now().minusYears(20),
                role
        ));
    }

    MedicalOrganization initMedicalOrganizations(String name, String address) {
        return medicalOrganizationService.save(MedicalOrganization.builder()
                .name(name)
                .address(address)
                .build());
    }

    @Test
    public void HrManagerOrganizationTest() throws Exception {
        Role role = initRole("HR_MANAGER");
        HrManager hrManager = initHrManager(role);

        accessToken = tokenUtil.obtainNewAccessToken(hrManager.getEmail(), "1", mockMvc);

        //Список медицинские организации пуст
        mockMvc.perform(get("/api/hr_manager/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))


        //Вывод всех медицинских организаций
        MedicalOrganization medicalOrganization = initMedicalOrganizations(
                "Больница №1", "Москва, Ленина, 1");
        MedicalOrganization medicalOrganization1 = initMedicalOrganizations(
                "Больница №21", "Москва, Ленина, 13");

        mockMvc.perform(get("/api/hr_manager/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].name", Is.is("Больница №1")))
                .andExpect(jsonPath("$.data[0].address", Is.is("Москва, Ленина, 1")))

                .andExpect(jsonPath("$.data[1].name", Is.is("Больница №21")))
                .andExpect(jsonPath("$.data[1].address", Is.is("Москва, Ленина, 13")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void HrManagerCreatOrganizationTest() throws Exception {
        Role role = initRole("HR_MANAGER");
        HrManager hrManager = initHrManager(role);

        //MedicalOrganization dto=initMedicalOrganizations("Больница 1", "Москва, Ленина, 1");

        MedicalOrganizationDto dto = MedicalOrganizationDto.builder()
                .name("Больница 1")
                .address("Москва, Ленина, 1")
                .build();

        accessToken = tokenUtil.obtainNewAccessToken(hrManager.getEmail(), "1", mockMvc);

        // Валидная мед.организация, создание мед.организацииD
        mockMvc.perform(post("/api/hr_manager/createMedicalOrganizations")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.name", Is.is(dto.getName())))
                .andExpect(jsonPath("$.data.address", Is.is(dto.getAddress())));
        // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //Существующее имя мед.организации, создание мед.организации
       // MedicalOrganization dto1 = initMedicalOrganizations("Больница 1", "Москва, Ленина, 10");

        MedicalOrganizationDto dto1 = MedicalOrganizationDto.builder()
                .name("Больница 1")
                .address("Москва, Ленина, 10")
                .build();

        mockMvc.perform(post("/api/hr_manager/createMedicalOrganizations")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto1))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(412)))
                .andExpect(jsonPath("$.text", Is.is(
                        "Такое имя медицинской организации уже используется!")));
        // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void HrManagerUpdateOrganizationTest() throws Exception {
        Role role = initRole("HR_MANAGER");
        HrManager hrManager = initHrManager(role);

        MedicalOrganization validMedOrganizationUpdate = initMedicalOrganizations("Больница №2", "Москва, Разина, 3");
        MedicalOrganization noValid = new MedicalOrganization(null, "Больница 1", "Москва, Разина, 3",
                null);
        validMedOrganizationUpdate.setAddress("Москва, Разина, 55");

        accessToken = tokenUtil.obtainNewAccessToken(hrManager.getEmail(), "1", mockMvc);

        //валидный, обнавление мед.организации
        mockMvc.perform(put("/api/hr_manager/updateMedicalOrganizations")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(validMedOrganizationUpdate))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(validMedOrganizationUpdate.getId().intValue())))
                .andExpect(jsonPath("$.data.name", Is.is("Больница №2")))
                .andExpect(jsonPath("$.data.address", Is.is("Москва, Разина, 55")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        // Не валидное ид, обновление мед.организации
        mockMvc.perform(put("/api/hr_manager/updateMedicalOrganizations")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(noValid))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(410)))
                .andExpect(jsonPath("$.text", Is.is(
                        "По переданному id медицинская организация не найдена.")));
        //        .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void HrManagerDeleteOrganizationTest() throws Exception {
        Role role = initRole("HR_MANAGER");
        HrManager hrManager = initHrManager(role);

        MedicalOrganization m1 = initMedicalOrganizations("Больница №45", "Москва, Ленина, 1");
        Long m2 = 8888888L;


        accessToken = tokenUtil.obtainNewAccessToken(hrManager.getEmail(), "1", mockMvc);


        //валидный, удаление мед.организации
        mockMvc.perform(delete("/api/hr_manager/deleteMedicalOrganization/{id}", m1.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)));
//          .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //проверка, что мед.организация удалена по ид
        Assertions.assertTrue(1L >= entityManager.createQuery("""
                        SELECT COUNT(m) FROM MedicalOrganization m
                        WHERE m.id = :id
                        """, Long.class)
                .setParameter("id", m1.getId())
                .getSingleResult());

        // Не валидный ид, удаление мед.организации
        mockMvc.perform(delete("/api/hr_manager/deleteMedicalOrganization/{id}", m2)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is("Медицинской организации с таким id нет!")));
        //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}
