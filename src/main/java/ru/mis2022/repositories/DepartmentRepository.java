package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //todo имя метода неправильное - убрать '_'
    List<Department> findAllByMedicalOrganization_Id(Long id);

    @Query("""
            SELECT d FROM Department d WHERE d.id = :id
            """)
        //todo имя метода неправильное
    Department existById(Long id);
}
