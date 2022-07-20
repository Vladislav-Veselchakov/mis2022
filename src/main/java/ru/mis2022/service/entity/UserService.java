package ru.mis2022.service.entity;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mis2022.models.entity.User;

public interface UserService extends UserDetailsService {

    User getCurrentUserByEmail(String email);

}
