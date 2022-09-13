package ru.mis2022.controllers.economist;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.dto.service.MedicalServiceDto;
import ru.mis2022.models.entity.Economist;
import ru.mis2022.models.entity.MedicalService;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.EconomistService;
import ru.mis2022.service.entity.MedicalServiceService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistMedicalServiceRestControllerIT  extends ContextIT {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    EconomistService economistService;

    @Autowired
    MedicalServiceService medicalServiceService;

    Role initRole(String name) {
        return roleService.save(Role.builder()
                .name(name)
                .build());
    }

    Economist initEconomist(Role role) {
        return economistService.persist(new Economist(
                "economist@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role
        ));
    }

    MedicalService initMedicalService(String identifier, String name){
        return medicalServiceService.save(MedicalService.builder()
                .identifier(identifier)
                .name(name)
                .build());
    }

    @Test
    public void persistMedicalServiceTest() throws Exception {
        Role role = initRole("ECONOMIST");
        Economist economist = initEconomist(role);

        accessToken = tokenUtil.obtainNewAccessToken(economist.getEmail(), "1", mockMvc);

        //валидный тест
        MedicalServiceDto dto1 = MedicalServiceDto.builder()
                .identifier("K12")
                .name("Обследование")
                .build();
        mockMvc.perform(post("/api/economist/medicalService/create")
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

        //в базе уже есть мед.услуга с таким идентификатором
        MedicalServiceDto dto2 = MedicalServiceDto.builder()
                .identifier("K12")
                .name("Лечение")
                .build();
        mockMvc.perform(post("/api/economist/medicalService/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is (400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(410)))
                .andExpect(jsonPath("$.text", Is.is("Медицинская услуга с данным  идентификатором уже существует")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //в базе уже есть мед.услуга с таким именем
        MedicalServiceDto dto3 = MedicalServiceDto.builder()
                .identifier("X12")
                .name("Обследование")
                .build();
        mockMvc.perform(post("/api/economist/medicalService/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto3))
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().is (400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(412)))
                .andExpect(jsonPath("$.text", Is.is("Медицинская услуга с таким именем уже существует")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

}
