package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Invite;
import ru.mis2022.repositories.InviteRepository;
import ru.mis2022.service.entity.InviteService;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    @Override
    public Invite save(Invite invite) {
        return inviteRepository.save(invite);
    }

    @Override
    public Invite findByUserId(Long userId) {
        return null;
    }
}
