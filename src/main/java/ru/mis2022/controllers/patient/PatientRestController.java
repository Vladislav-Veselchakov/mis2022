package ru.mis2022.controllers.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.patient.CurrentPatientDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.dto.PatientDtoService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient")
public class PatientRestController {

    private final PatientDtoService patientDtoService;

    @GetMapping("/mainPage/current")
    public CurrentPatientDto getCurrentPatientDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return patientDtoService.getCurrentPatientDtoByEmail(currentUser.getEmail());
    }
}
