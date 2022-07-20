package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Role;

public interface RoleService {

    Role persist(Role role);

    Role findByName(String name);
}
