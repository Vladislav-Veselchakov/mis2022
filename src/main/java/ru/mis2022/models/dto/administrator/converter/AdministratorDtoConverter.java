package ru.mis2022.models.dto.administrator.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.administrator.AdministratorDto;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.service.entity.RoleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;


@Component
public class AdministratorDtoConverter {
    private final RoleService roleService;


    public AdministratorDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }


    public AdministratorDto toDto(Administrator entity) {
        String roleName = String.valueOf(entity.getRole());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String birthday = entity.getBirthday().format(formatter);

        return AdministratorDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .surname(entity.getSurname())
                .birthday(birthday.replaceAll("-", "."))
                .role(roleName.substring(roleName.lastIndexOf("=")).replaceAll("[^A-Z]", ""))
                .build();
    }

    public Administrator toEntity(AdministratorDto dto) {
        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd.MM.yyyy")
                .toFormatter(Locale.ENGLISH);

        return new Administrator(
                dto.getEmail(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getSurname(),
                LocalDate.parse(dto.getBirthday(), df),
                roleService.findByName(dto.getRole())
        );
    }
}
