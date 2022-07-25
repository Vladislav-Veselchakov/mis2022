package ru.mis2022.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Economist;
import ru.mis2022.models.entity.HrManager;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.Registrar;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.EconomistService;
import ru.mis2022.service.entity.HrManagerService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RegistrarService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.service.entity.AdministratorService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static ru.mis2022.models.entity.Role.RolesEnum;


@Component
@ConditionalOnExpression("${mis.property.runInitialize:true}")
public class DataInitializer {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final EconomistService economistService;
    private final RoleService roleService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final DepartmentService departmentService;
    private final RegistrarService registrarService;
    private final AdministratorService administratorService;
    private final HrManagerService hrManagerService;


    public DataInitializer(PatientService patientService,
                           DoctorService doctorService,
                           EconomistService economistService,
                           RoleService roleService,
                           MedicalOrganizationService medicalOrganizationService,
                           DepartmentService departmentService,
                           RegistrarService registrarService,
                           AdministratorService administratorService,
                           HrManagerService hrManagerService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.economistService = economistService;
        this.roleService = roleService;
        this.medicalOrganizationService = medicalOrganizationService;
        this.departmentService = departmentService;
        this.registrarService = registrarService;
        this.administratorService = administratorService;
        this.hrManagerService = hrManagerService;
    }

    @PostConstruct
    public void addTestData() {
        Role roleRegistrar = roleService.persist(new Role(RolesEnum.REGISTRAR.name()));
        Role roleDoctor = roleService.persist(new Role(RolesEnum.DOCTOR.name()));
        Role rolePatient = roleService.persist(new Role(RolesEnum.PATIENT.name()));
        Role roleMainDoctor = roleService.persist(new Role(RolesEnum.MAIN_DOCTOR.name()));
        Role roleEconomist = roleService.persist(new Role(RolesEnum.ECONOMIST.name()));
        Role roleAdmin = roleService.persist((new Role(RolesEnum.ADMIN.name())));
        Role roleHrManager = roleService.persist(new Role(RolesEnum.HR_MANAGER.name()));

        for (int num = 1; num < 10; num++) {
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

        for (int num = 1; num < 10; num++) {
            economistService.persist(new Economist(
                    "economist" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    roleEconomist
            ));
        }

        for (int num = 1; num < 2; num++) {
            doctorService.persist(new Doctor(
                    "mainDoctor" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    roleMainDoctor,
                    department
            ));
        }

        for (int num = 1; num < 2; num++) {
            registrarService.persist(new Registrar(
                    "registrar" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    roleRegistrar
            ));
        }
        for (int num = 1; num < 2; num++) {
            administratorService.persist(new Administrator(
                    "administrator" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    roleAdmin
            ));
        }

        for (int num = 1; num < 2; num++) {
            hrManagerService.persist(new HrManager(
                    "hrManager" + num + "@email.com",
                    String.valueOf(num),
                    "f_name_" + num,
                    "l_name_" + num,
                    "surname_" + num,
                    LocalDate.now().minusYears(20),
                    roleHrManager
            ));
        }
    }
}
