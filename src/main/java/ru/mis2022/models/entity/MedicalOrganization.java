package ru.mis2022.models.entity;


import javax.persistence.OneToMany;
import java.util.List;

/**
 * медицинская организация
 */

public class MedicalOrganization {

    private Long id;

    private String name;

    private String address;

    //@OneToMany
    private List<Department> departments;
}
