package ru.mis2022.controllers.economist;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.dto.disease.DiseaseDto;
import ru.mis2022.models.entity.Disease;
import ru.mis2022.models.entity.Economist;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DiseaseService;
import ru.mis2022.service.entity.EconomistService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistDiseaseRestControllerIT extends ContextIT {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    EconomistService economistService;

    @Autowired
    DiseaseService diseaseService;


    Role initRole(String name) {
        return roleService.persist(Role.builder()
                .name(name)
                .build());
    }

    Economist initEconomist(Role role) {
        return economistService.persist(new Economist(
                "economist1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role
        ));
    }

    Disease initDisease(String identifier, String name){
        return diseaseService.save(Disease.builder()
                .identifier(identifier)
                .name(name)
                .build());
    }


    @Test
    public void getAllDiseaseTest() throws Exception {
        Role role = initRole("ECONOMIST");
        Economist economist = initEconomist(role);

        accessToken = tokenUtil.obtainNewAccessToken(economist.getEmail(), "1", mockMvc);

        //КЕЙС КОГДА В БАЗЕ НЕТ ЗАБОЛЕВАНИЙ
        mockMvc.perform(get("/api/economist/disease/getAll")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //ВАЛИДНЫЙ КЕЙС
        Disease disease1 = initDisease("12345", "dis1name");
        Disease disease2 = initDisease("98765", "dis2name");

        mockMvc.perform(get("/api/economist/disease/getAll")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(2)))

                .andExpect(jsonPath("$.data[0].id", Is.is(disease1.getId().intValue())))
                .andExpect(jsonPath("$.data[0].identifier", Is.is(disease1.getIdentifier())))
                .andExpect(jsonPath("$.data[0].name", Is.is(disease1.getName())))

                .andExpect(jsonPath("$.data[1].id", Is.is(disease2.getId().intValue())))
                .andExpect(jsonPath("$.data[1].identifier", Is.is(disease2.getIdentifier())))
                .andExpect(jsonPath("$.data[1].name", Is.is(disease2.getName())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void persistDiseaseTest() throws Exception {
        Role role = initRole("ECONOMIST");
        Economist economist = initEconomist(role);

        accessToken = tokenUtil.obtainNewAccessToken(economist.getEmail(), "1", mockMvc);

        //ВАЛИДНЫЙ ТЕСТ
        DiseaseDto dto1 = DiseaseDto.builder()
                .identifier("12345")
                .name("Covid-19")
                .build();
        mockMvc.perform(post("/api/economist/disease/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto1))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.identifier", Is.is(dto1.identifier())))
                .andExpect(jsonPath("$.data.name", Is.is(dto1.name())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //В БАЗЕ УЖЕ ЕСТЬ ЗАБОЛЕВАНИЕ С ТАКИМ ИДЕНТИФИКАТОРОМ
        DiseaseDto dto2 = DiseaseDto.builder()
                .identifier("12345")
                .name("Covid-20")
                .build();
        mockMvc.perform(post("/api/economist/disease/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is (400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(410)))
                .andExpect(jsonPath("$.text", Is.is("Заболевание с данным идентификатором уже существует")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //В БАЗЕ ЕСТЬ ЗАБОЛЕВАНИЕ С ТАКИМ ИМЕНЕМ НО ДРУГИМ ИДЕНТИФИКАТОРОМ
        DiseaseDto dto3 = DiseaseDto.builder()
                .identifier("98765")
                .name("Covid-19")
                .build();
        mockMvc.perform(post("/api/economist/disease/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto3))
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.identifier", Is.is(dto3.identifier())))
                .andExpect(jsonPath("$.data.name", Is.is(dto3.name())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void deleteDiseaseByIdTest() throws Exception {
        Role role = initRole("ECONOMIST");
        Economist economist = initEconomist(role);
        Disease disease1 = initDisease("12345", "dis1name");
        Disease disease2 = initDisease("98765", "dis2name");

        accessToken = tokenUtil.obtainNewAccessToken(economist.getEmail(), "1", mockMvc);

        //ВАЛИДНЫЙ ТЕСТ
        mockMvc.perform(delete("/api/economist/disease/delete/{diseaseId}", disease1.getId())
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //ПРОВЕРЯЮ ПО ИД, ЧТО ЗАБОЛЕВАНИЯ БОЛЬШЕ НЕТ
        Assertions.assertTrue(1L >= entityManager.createQuery("""
                        SELECT COUNT(d) FROM Disease d
                        WHERE d.id = :id
                        """, Long.class)
                .setParameter("id", disease1.getId())
                .getSingleResult());

        //ПРОВЕРЯЮ, ЧТО ДРУГОЕ ЗАБОЛЕВАНИЯ НЕ УДАЛЕНО
        Assertions.assertNotNull(entityManager.createQuery("""
                        SELECT d FROM Disease d
                        WHERE d.id = :id
                        """, Disease.class)
                .setParameter("id", disease2.getId())
                .getSingleResult());

        //ПОПЫТКА УДАЛИТЬ НЕСУЩЕСТВУЩЕЕ ЗАБОЛЕВАНИЕ
        mockMvc.perform(delete("/api/economist/disease/delete/{diseaseId}", disease1.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(411)))
                .andExpect(jsonPath("$.text", Is.is("Заболевание с переданным id не существует")))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}