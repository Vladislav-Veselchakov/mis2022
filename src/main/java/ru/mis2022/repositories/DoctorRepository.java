package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;

import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.doctor.CurrentDoctorDto(
            p.firstName,
            p.lastName,
            p.birthday,
            r.name,
            d.name)
        FROM Doctor p
            JOIN Role r ON p.role.id = r.id
            JOIN Department d ON p.department.id = d.id
        WHERE p.email = :email
        """)
    CurrentDoctorDto getCurrentDoctorDtoByEmail(String email);

    Doctor findByEmail(String email);

    @Query("""
        SELECT new ru.mis2022.models.dto.doctor.CurrentDoctorDto(
            doc.firstName,
            doc.lastName,
            doc.birthday,
            role.name,
            dep.name)
        FROM Doctor doc
            JOIN Role role ON doc.role.id = role.id
            JOIN Department dep ON doc.department.id = dep.id
        WHERE doc.email = :email
        AND doc.department = :department
        """)
        //todo имя метода неправильное - отобразить получение коллекции
    List<CurrentDoctorDto> findDoctorDtoByDepartment(Department department);

    //todo надо искать по ид департамента и необходимо писать запрос с джойнами
    List<Doctor> findByDepartment (Department department);

    //todo дубль удалить
    List<Doctor> findAllByDepartmentId(Long id);

    @Query("""
            SELECT d FROM Doctor d WHERE d.id = :id
            """)
    Doctor findDoctorById(Long id);

}
