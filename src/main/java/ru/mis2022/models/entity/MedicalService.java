package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * MedicalService - Медицинская услуга
 * есть прейскурант всех услуг которые оказывает ЛПУ,
 * у каждой услуги есть уникальный идентификатор, например K321101
 * экономист наполняет список услуг каждого отделения из общего
 * Услуга может стоить 0 УЕТ, что означает, что она бесплатная.
 * Каждая услуга стоит определенный УЕТ в фиксированный период времени, кратный дням,
 * например:
 * с 28.07.2021 по 25.09.2021 цена 0.25
 * с 26.09.2021 по 03.12.2021 цена 0.30
 * с 04.12.2021 по null       цена null (означает - с 4.12.21 услуга не оказывается)
 * с 01.01.2022 по null       цена 0.20 (означает - с 01.01.2022 по настоящее услуга оказывается)
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<PriceOfMedicalService> prices;
}