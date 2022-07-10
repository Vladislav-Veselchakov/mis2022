package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.dto.user.CurrentUserDto;
import ru.mis2022.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    User findByEmail(String email);

    @Query("""
    SELECT
        u.firstName,
        u.lastName
    FROM User u
    WHERE u.email = :email
    """)
    CurrentUserDto getCurrentUserDtoByEmail(String email);
}
