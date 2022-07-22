package ru.mis2022.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

/**
 * Appeal - Обращение
 * <p>
 * Пациент обращается с заболеванием. И лечат заболевание.
 * При первом посещении устанавливается заболевание, которое будут лечить.
 * Обращение может лечиться как в одно посещение, так и в несколько.
 * Врач, оказывающий услуги в рамках одного обращения, может меняться(заболел), но не должна меняться специализация врача.
 * Любой из врачей, участвовавших в лечении пациента, может закрыть/открыть обращение.
 * Пока обращение не закрыто, врач может его править (только те посещения, где лечил он).
 * Когда обращение закрыто, оно может попасть в счет и пока оно в счете, его нельзя править никому.(isClosed = true)
 * Обращение может быть удалено из сформированного счета для модификации
 * Любой из врачей, участвовавших в лечении пациента может открыть обращение для устранения ошибок, если оно не в счете
 * Закрытое обращение нельзя модифицировать
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Disease disease;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Visit> visits;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private boolean isClosed = false;

    private LocalDate localDate = LocalDate.now();
}
