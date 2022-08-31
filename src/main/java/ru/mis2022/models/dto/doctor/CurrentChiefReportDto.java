package ru.mis2022.models.dto.doctor;

public record CurrentChiefReportDto (
        Long doctorId,
        String doctorFullName,
        String date,
        Long busyTalons,
        Long totalTalons
){}
