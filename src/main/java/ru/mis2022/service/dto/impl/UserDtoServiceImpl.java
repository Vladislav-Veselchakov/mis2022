package ru.mis2022.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.dto.user.UserDto;
import ru.mis2022.repositories.UserRepository;
import ru.mis2022.service.dto.UserDtoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDtoServiceImpl implements UserDtoService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findDoctorsByDepartment(Long deptId) {
        return userRepository.findDoctorsByDepartment(deptId);
    }

    public List<UserDto> findStaffByDepartment(Long depId) {
        return userRepository.findStaffByDepartment(depId);
    }
}
