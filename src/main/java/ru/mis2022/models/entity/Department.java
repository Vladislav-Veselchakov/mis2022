package ru.mis2022.models.entity;


import java.util.List;

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

public class Department {
    private Long id;
    private String name;
    private List<Doctor> doctors;
    private List<Disease> diseases;
    private List<MedicalService> medicalServices;
    private MedicalOrganization medicalOrganization;
}
