package ru.mis2022.service.entity;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.User;

public interface UserService extends UserDetailsService {

    User getCurrentUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User findByEmailAndExceptCurrentId(String email, Long id);

}
