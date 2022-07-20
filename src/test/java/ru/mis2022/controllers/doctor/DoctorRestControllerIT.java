package ru.mis2022.controllers.doctor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mis2022.models.entity.Registrar;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.UserService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.mis2022.models.entity.Role.RolesEnum;
import static ru.mis2022.models.entity.Role.builder;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DoctorRestControllerIT {

    @Autowired UserService userService;

    @Autowired RoleService roleService;

    @PersistenceContext EntityManager entityManager;

    Role initNewRole(String name) {
        return builder()
                .name(name)
                .build();
    }

    User initNewRegistrar(Role role) {
        Registrar registrar = new Registrar();
        registrar.setEmail("test@email.com");
        registrar.setPassword("1");
        registrar.setFirstName("f");
        registrar.setLastName("l");
        registrar.setBirthday(LocalDate.now());
        registrar.setEnabled(true);
        registrar.setRole(role);
        return registrar;
    }

    @Test
    @Disabled
    public void getCurrentUserTest() {

        Role role = roleService.persist(initNewRole(RolesEnum.REGISTRAR.name()));
//        userService.persist(initNewRegistrar(role));

        User user = entityManager.createQuery("""
                        select r from Registrar r
                            join fetch r.role
                        where r.email =: email
                        """, Registrar.class
                )
                .setParameter("email", "test@email.com").getSingleResult();

        assertNotNull(user.getId());
        assertEquals(user.getRole(), role);
    }
}