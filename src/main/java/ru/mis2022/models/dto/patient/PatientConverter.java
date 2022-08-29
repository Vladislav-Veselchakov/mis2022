package ru.mis2022.models.dto.patient;

import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Patient;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatientConverter {

    public List<PatientDto> toPatientDto (List<Patient> patients) {
    List<PatientDto> patientDtos = new ArrayList<>();
    for (Patient patient: patients) {
        patientDtos.add(PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .surName(patient.getSurname())
                .birthday(patient.getBirthday())
                .passport(patient.getPassport().replaceAll("\\s+","").substring(6))
                .polis(patient.getPolis().substring(12))
                .snils(patient.getSnils().replaceAll("[^A-Za-zА-Яа-я0-9]", "").substring(7))
                .build());
    }
        return patientDtos;
    }
}


