package ru.mis2022.service.entity;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mis2022.models.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getCurrentUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User findByEmailAndExceptCurrentId(String email, Long id);

    List<User> findPersonalByFullName(String fullName, String roleName);

    User persist(User user);
}
