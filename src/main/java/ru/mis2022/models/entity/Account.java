package ru.mis2022.models.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Account - Счет
 * В последний день каждого месяца экономист формирует счет на оплату в министерство здравоохранения.
 * В этот счет попадают все закрытые обращения в рамках ОМС.
 * Обращение может находиться только в одном счете.
 * Обращения, которые находятся в счете нельзя модифицировать
 * счет можно удалять и пересоздавать - Обращения не должны удаляться.
 * из счета можно удалять обращения - они не должны удаляться
 * можно проводить поиск новых закрытых обращений, которых нет в счете для добавления в счет
 */


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Appeal> appeals;

    private Long money;
}