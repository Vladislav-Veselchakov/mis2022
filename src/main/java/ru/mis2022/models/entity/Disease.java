package ru.mis2022.models.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Disease Заболевание
 * есть классификатор заболеваний, которые лечит ЛПУ
 * Экономист наполняет список заболеваний каждого отделения из общего
 * у каждого заболевания есть уникальный идентификатор, например A04
 * есть какая-то взаимосвязь - при определенных заболеваниях можно использовать определенные услуги.
 * но сейчас ее знают только врачи
 */

@Entity
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String name;

    @ManyToOne
    private Department department;

}
