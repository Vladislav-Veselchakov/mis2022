package ru.mis2022.service.dto;

import ru.mis2022.models.dto.user.UserDto;

import java.util.List;

public interface UserDtoService {

    List<UserDto> findDoctorsByDepartment(Long depId);

    List<UserDto> findStaffByDepartment(Long depId);


}
