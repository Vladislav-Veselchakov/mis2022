package ru.mis2022.models.entity;


import javax.persistence.*;

/**
 * Disease Заболевание
 * есть классификатор заболеваний, которые лечит ЛПУ
 * Экономист наполняет список заболеваний каждого отделения из общего
 * у каждого заболевания есть уникальный идентификатор, например A04
 * есть какая-то взаимосвязь - при определенных заболеваниях можно использовать определенные услуги.
 * но сейчас ее знают только врачи
 */

@Entity
@Table(name = "disease")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

}
