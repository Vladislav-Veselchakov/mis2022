package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Invite;
public interface InviteService {
    Invite save(Invite invite);
    Invite findByUserId(Long userId);
}
