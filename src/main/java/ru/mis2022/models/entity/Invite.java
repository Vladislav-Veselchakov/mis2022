package ru.mis2022.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String token;
    private LocalDateTime expirationDate;
    private Long userId;

    public Invite(String token, LocalDateTime expirationDate, Long userId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }
}
