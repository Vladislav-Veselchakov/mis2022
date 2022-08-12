package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.entity.Yet;
import java.time.LocalDate;
import java.util.List;

public interface YetRepository extends JpaRepository<Yet, Long> {

    @Query("""
                select y from Yet y where y.dayFrom <= :dayTo and y.dayTo >= :dayFrom 
            """)
    List<Yet> existYetDayFromDayTo(LocalDate dayFrom, LocalDate dayTo);

    @Query("""
                select y from Yet y where y.dayFrom <= :dayTo and y.dayTo >= :dayFrom and y.id <> :id
            """)
    List<Yet> existYetDayFromDayToExceptCurrentId(Long id, LocalDate dayFrom, LocalDate dayTo);

    @Query("""
                select y from Yet y where y.id = :id 
            """)
    Yet existById(Long id);

    //todo метод есть в репозитории. удалить
    List<Yet> findAll();

    //todo метод есть в репозитории. удалить
    void deleteById(Long id);

}
