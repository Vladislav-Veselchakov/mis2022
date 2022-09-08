package ru.mis2022.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mis2022.utils.validation.OnCreate;
import ru.mis2022.utils.validation.OnUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

/**
 * Department - Отделение
 * <p>
 * От типа отделения зависят услуги оказываемые в нем.
 * В нашем ЛПУ есть отделения: терапевтическое, хирургическое, ортодонтическое
 * в каждом отделении есть:
 * список врачей одной специальности, оказывающие одинаковые услуги,
 * заведующий отделением, который тоже является врачом этого отделения,
 * список заболеваний которые лечат в отделении
 * список услуг оказываеммых по ОМС (некоторые услуги могут оказываться в разных отделениях)
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = OnCreate.class, message = "id должен быть равен null")
    @Positive(groups = OnUpdate.class, message = "id должен быть положительным")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Doctor> doctors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Disease> diseases;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<MedicalService> medicalServices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id")
    private MedicalOrganization medicalOrganization;

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, MedicalOrganization medicalOrganization) {
        this.name = name;
        this.medicalOrganization = medicalOrganization;
    }
}
