package ru.mis2022.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.RoleService;
import ru.mis2022.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


@Component
@ConditionalOnExpression("${app.runInitialize:true}")
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void addTestData() {
        Role roleAdmin = new Role(Role.RolesEnum.USER.name());
        roleService.persist(roleAdmin);
        User testUser = new User();
        testUser.setEmail("email");
        testUser.setPassword("password");
        testUser.setBirthday(LocalDate.now());
        testUser.setFirstName("first");
        testUser.setLastName("last");
        testUser.setRole(roleAdmin);
        userService.persist(testUser);
    }
}
