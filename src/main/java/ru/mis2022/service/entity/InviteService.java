package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Invite;

import java.time.LocalDateTime;

public interface InviteService {
    Invite save(Invite invite);
    Invite findByUserId(Long userId);
}
