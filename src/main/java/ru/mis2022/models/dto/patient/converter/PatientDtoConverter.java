package ru.mis2022.models.dto.patient.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.patient.PatientDto;
import ru.mis2022.models.entity.Patient;

@Component
public class PatientDtoConverter {

    public PatientDto patientToPatientDto(Patient patient) {
        if (patient == null) {
            return null;
        }
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .surName(patient.getSurname())
                .birthday(patient.getBirthday())
                .passport(patient.getPassport())
                .polis(patient.getPolis())
                .snils(patient.getSnils())
                .build();
    }

}
