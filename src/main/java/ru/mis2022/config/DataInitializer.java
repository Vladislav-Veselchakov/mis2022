package ru.mis2022.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static ru.mis2022.models.entity.Role.RolesEnum;


@Component
@ConditionalOnExpression("${mis.property.runInitialize:true}")
public class DataInitializer {
    private final PatientService patientService;
    private final RoleService roleService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final DepartmentService departmentService;

    public DataInitializer(PatientService patientService,
                           RoleService roleService,
                           MedicalOrganizationService medicalOrganizationService,
                           DepartmentService departmentService) {
        this.patientService = patientService;
        this.roleService = roleService;
        this.medicalOrganizationService = medicalOrganizationService;
        this.departmentService = departmentService;
    }

    @PostConstruct
    public void addTestData() {
        Role roleRegistrar = roleService.persist(new Role(RolesEnum.REGISTRAR.name()));
        Role roleDoctor = roleService.persist(new Role(RolesEnum.DOCTOR.name()));
        Role rolePatient = roleService.persist(new Role(RolesEnum.PATIENT.name()));

        //patient1@email.com
        for (int num = 1; num < 10; num ++) {
            patientService.persist(new Patient(
                    "patient" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    rolePatient,
                    "passport_" + num,
                    "polis_" + num,
                    "snils_" + num,
                    "address_" + num));
        }

        MedicalOrganization medicalOrganization = new MedicalOrganization();
        medicalOrganization.setName("City Hospital");
        medicalOrganization.setAddress("Moscow, Pravda street, 30");
        medicalOrganizationService.persist(medicalOrganization);

        Department department = new Department();
        department.setName("Therapy");
        department.setMedicalOrganization(medicalOrganization);
        Department department2 = new Department();
        department2.setName("Surgery");
        department2.setMedicalOrganization(medicalOrganization);
        departmentService.persist(department);
        departmentService.persist(department2);
    }
}
