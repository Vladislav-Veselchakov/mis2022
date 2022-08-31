package ru.mis2022.service.dto;

import ru.mis2022.models.dto.doctor.ChiefReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ChiefReportService {
    List<ChiefReportDto> getReport(LocalDate dateStart, LocalDate dateEnd);
}
