package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mis2022.models.dto.talon.TalonDto;
import ru.mis2022.models.dto.talon.DoctorTalonsDto;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Talon;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface TalonRepository extends JpaRepository<Talon, Long> {

    @Query("""
        SELECT new ru.mis2022.models.dto.talon.TalonDto(
        t.id,
        t.time,
        t.doctor.id,
        t.patient.id)
        FROM Talon t
        WHERE t.doctor.id = :doctorId
        """)
    Optional<List<TalonDto>> findAllDtosByDoctorId(long doctorId);

    List<Talon> findAllByDoctorId(Long id);

    List<Talon> findAllByPatientId(Long id);

    @Query("""
                select count(t) from Talon t
                where t.doctor.id = :doctorId
                    and t.time between :startTime and :endTime
            """)
    long findCountTalonsByParameters(long doctorId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT t FROM Talon t WHERE t.id = :id")
    Talon findTalonById(Long id);

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

    @Query("""
            SELECT new ru.mis2022.models.dto.talon.DoctorTalonsDto(
            t.id,
            t.time,
            t.patient.id,
            p.firstName,
            p.lastName,
            p.surname
            )
            FROM Talon t 
            JOIN Doctor d 
                ON t.doctor.id = d.id
            LEFT JOIN Patient p 
                ON p.id = t.patient.id
            WHERE
                t.time BETWEEN :startDayTime AND :endDayTime 
                AND d.id = :doctorId
            ORDER BY
                t.time
            """)
    List<DoctorTalonsDto> talonsByDoctorByDay(long doctorId, LocalDateTime startDayTime, LocalDateTime endDayTime);
}
