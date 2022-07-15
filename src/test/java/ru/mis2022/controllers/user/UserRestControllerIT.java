package ru.mis2022.controllers.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.RoleService;
import ru.mis2022.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRestControllerIT {

    @Autowired UserService userService;

    @Autowired RoleService roleService;

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired MockMvc mockMvc;

    @PersistenceContext EntityManager entityManager;

    User initNewUser(Role role) {
        return User.builder()
                .firstName(anyString())
                .lastName(anyString())
                .surname(anyString())
                .email("test@email.com")
                .password(anyString())
                .enabled(true)
                .birthday(LocalDate.now())
                .role(role)
                .build();
    }

    Role initNewUserRole() {
        return Role.builder()
                .name(Role.RolesEnum.USER.name())
                .build();
    }

    @Test
//    @WithMockUser(authorities = "ADMIN")
    public void getCurrentUserTest() throws Exception {

        var role = roleService.persist(initNewUserRole());
        userService.persist(initNewUser(role));

        User user = entityManager.createQuery("""
                        select u from User u
                            join fetch u.role
                        where u.email =: email
                        """, User.class
                )
                .setParameter("email", "test@email.com").getSingleResult();

//        System.out.println(user);
        assertNotNull(user.getId());
        assertEquals(user.getRole(), role);

//        mockMvc.perform(
//                        get("/user/current")
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success", Is.is(true)))
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}