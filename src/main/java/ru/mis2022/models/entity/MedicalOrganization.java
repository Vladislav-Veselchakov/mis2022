package ru.mis2022.models.entity;


import java.util.List;

/**
 * медицинская организация
 */

public class MedicalOrganization {
    private Long id;
    private String name;
    private String address;
    private List<Department> departments;
}
