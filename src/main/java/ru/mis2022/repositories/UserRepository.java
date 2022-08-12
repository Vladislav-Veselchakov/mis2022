package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mis2022.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    User findByEmail(String email);

    boolean existsByEmail(String email);

    //todo метод есть в репозитории. удалить
    boolean existsById(Long id);

    @Query("""
            SELECT u FROM User u JOIN FETCH u.role WHERE (u.email = :email AND u.id <> :id)
            """)
    User findByEmailAndExceptCurrentId(String email, Long id);

}
