package ru.mis2022.models.dto.user.converter;

import org.springframework.stereotype.Component;
import ru.mis2022.models.dto.doctor.DoctorDto;
import ru.mis2022.models.dto.user.UserDto1;
import ru.mis2022.models.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserDto1 toDto(User entityUser) {
        UserDto1 userDto = new UserDto1();
        userDto.setId(entityUser.getId());
        userDto.setEmail(entityUser.getEmail());
        userDto.setFirstName(entityUser.getFirstName());
        userDto.setLastName(entityUser.getLastName());
        userDto.setSurname(entityUser.getSurname());
        userDto.setBirthday(entityUser.getBirthday().toString());
        userDto.setRole(entityUser.getRole().toString());

        return userDto;
    }

    public User toEntity(UserDto1 userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setSurname(userDto.getSurname());
        user.setBirthday(LocalDate.parse(userDto.getBirthday()));
        userDto.setRole(userDto.getRole());

        return user;
    }

    public List<UserDto1> convertToUserDtoFromDoctorDto(List<DoctorDto> doctorDtos) {
        List<UserDto1> userDtos = new ArrayList<>();
        for (DoctorDto doctorDto : doctorDtos) {
            UserDto1 userDto = new UserDto1();
            userDto.setId(doctorDto.getId());
            userDto.setEmail(doctorDto.getEmail());
            userDto.setPassword(doctorDto.getPassword());
            userDto.setFirstName(doctorDto.getFirstName());
            userDto.setLastName(doctorDto.getLastName());
            userDto.setSurname(doctorDto.getSurname());
            userDto.setBirthday(doctorDto.getBirthday());
            userDto.setRole(doctorDto.getRole());
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
