package ru.mis2022.models.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * PersonalHistory - Личные данные
 */

@Entity
public class PersonalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Attestation> attestations;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Diploma> diplomas;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Vacation> vacations;

    private LocalDate dateOfEmployment;

    private LocalDate dateOfDismissal;
}
