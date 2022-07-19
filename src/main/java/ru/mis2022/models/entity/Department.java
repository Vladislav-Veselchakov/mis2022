package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private Set<Doctor> doctors;

    @OneToMany
    private Set<Disease> diseases;

    @ManyToMany
    private Set<MedicalService> medicalServices;

    @ManyToOne
    private MedicalOrganization medicalOrganization;
}
