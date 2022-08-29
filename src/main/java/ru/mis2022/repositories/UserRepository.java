package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndId(String name, Long id);

    @Query("""
            SELECT u FROM User u JOIN FETCH u.role WHERE (u.email = :email AND u.id <> :id)
            """)
    User findByEmailAndExceptCurrentId(String email, Long id);

    @Query("""
            SELECT u FROM User u
                JOIN FETCH u.role
            WHERE u.role.name <> :roleName
                AND LOWER(CONCAT(u.lastName,' ',u.firstName))
                LIKE LOWER(CONCAT('%',:fullName,'%'))
            ORDER BY u.lastName, u.firstName, u.id
            """)
    List<User> findPersonalByFullName(String fullName, String roleName);
}
