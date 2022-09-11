package ru.mis2022.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String token;
    private LocalDateTime expirationDate;
    @Column(unique = true)
    private Long userId;

    public Invite(String token, LocalDateTime expirationDate, Long userId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }
}
