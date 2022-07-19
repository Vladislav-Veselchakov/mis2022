package ru.mis2022.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.DepartmentService;
import ru.mis2022.service.MedicalOrganizationService;
import ru.mis2022.service.RoleService;
import ru.mis2022.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static ru.mis2022.models.entity.Role.*;


@Component
@ConditionalOnExpression("${mis.property.runInitialize:true}")
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final DepartmentService departmentService;

    public DataInitializer(UserService userService, RoleService roleService, MedicalOrganizationService medicalOrganizationService, DepartmentService departmentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.medicalOrganizationService = medicalOrganizationService;
        this.departmentService = departmentService;
    }

    @PostConstruct
    public void addTestData() {
        Role roleDoctor = new Role(RolesEnum.DOCTOR.name());
        Role roleRegistrar = new Role(RolesEnum.REGISTRAR.name());
        roleService.persist(roleDoctor);
        roleService.persist(roleRegistrar);


        User testUser1 = new User();
        testUser1.setEmail("email1");
        testUser1.setPassword("1");
        testUser1.setBirthday(LocalDate.now());
        testUser1.setFirstName("first1");
        testUser1.setLastName("last1");
        testUser1.setRole(roleDoctor);
        userService.persist(testUser1);

        User testUser2 = new User();
        testUser2.setEmail("email2");
        testUser2.setPassword("2");
        testUser2.setBirthday(LocalDate.now());
        testUser2.setFirstName("first2");
        testUser2.setLastName("last2");
        testUser2.setRole(roleRegistrar);
        userService.persist(testUser2);

        MedicalOrganization medicalOrganization = new MedicalOrganization();
        medicalOrganization.setName("Hospital");
        medicalOrganization.setAddress("Moscow, Pravda street, 20");
        medicalOrganizationService.persist(medicalOrganization);

        Department department = new Department();
        department.setName("Therapy");
        department.setMedicalOrganization(medicalOrganization);
        Department department1 = new Department();
        department1.setName("Surgery");
        department1.setMedicalOrganization(medicalOrganization);
        departmentService.persist(department);
        departmentService.persist(department1);
    }
}
