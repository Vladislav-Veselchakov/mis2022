package ru.mis2022.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mis2022.models.entity.User;

public interface UserService extends UserDetailsService {

    void persist(User user);

    void merge(User user);

    User getUserByEmail(String email);

    User getCurrentUserByEmail(String email);

}
