package ru.kasimov.subscription.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kasimov.subscription.domain.dto.CreateUserDto;
import ru.kasimov.subscription.domain.dto.EditUserDto;
import ru.kasimov.subscription.domain.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // ====================== from DTOs ======================
    User createUserDtoToUser(CreateUserDto createUserDto);
    User editUserDtoToUser(EditUserDto editUserDto);

    // ====================== to DTOs ========================
    CreateUserDto userToCreateUserDto(User user);
    EditUserDto userToEditUserDto(User user);

}
