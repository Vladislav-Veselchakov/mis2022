package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * медицинская организация
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "medicalOrganization", fetch = FetchType.LAZY)
    private List<Department> departments;
}
