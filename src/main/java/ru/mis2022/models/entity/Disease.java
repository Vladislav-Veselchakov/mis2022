package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Disease Заболевание
 * есть классификатор заболеваний, которые лечит ЛПУ
 * Экономист наполняет список заболеваний каждого отделения из общего
 * у каждого заболевания есть уникальный идентификатор, например A04
 * есть какая-то взаимосвязь - при определенных заболеваниях можно использовать определенные услуги.
 * но сейчас ее знают только врачи
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
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
