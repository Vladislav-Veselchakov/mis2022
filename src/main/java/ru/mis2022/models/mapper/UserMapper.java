package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.user.UserDto;
import ru.mis2022.models.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "birthday", dateFormat = "dd.MM.yyyy")
    @Mapping(source = "role.name", target = "roleName")
    UserDto toDto(User user);

    List<UserDto> toListDto(List<User> users);
}
