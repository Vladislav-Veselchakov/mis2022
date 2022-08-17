package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;

import java.util.List;

import java.time.LocalDateTime;

public interface TalonRepository extends JpaRepository<Talon, Long> {

    //todo неправильное имя метода - убрать _
    List<Talon> findAllByDoctorId(Long id);

    List<Talon> findAllByPatientId(Long id);

    @Query("""
                select count(t) from Talon t
                where t.doctor.id = :doctorId
                    and t.time between :startTime and :endTime
            """)
    long findCountTalonsByParameters(long doctorId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT t FROM Talon t WHERE t.id = :id")
        //todo неправильное имя метода
    Talon isExistById(Long id);

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.id IN (
                SELECT DISTINCT (d.id) FROM Doctor d
                    JOIN Department dep ON d.department.id = :departmentId
                    JOIN Talon t ON d.id = t.doctor.id
                WHERE t.time BETWEEN :startTime AND :endTime
                    AND t.patient IS NULL)
            """)
    List<Doctor> findDoctorsWithTalonsSpecificTimeRange(LocalDateTime startTime, LocalDateTime endTime, Long departmentId);

}
