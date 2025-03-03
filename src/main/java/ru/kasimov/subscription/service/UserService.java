package ru.kasimov.subscription.service;

import ru.kasimov.subscription.domain.model.User;
import ru.kasimov.subscription.domain.dto.CreateUserDto;
import ru.kasimov.subscription.domain.dto.EditUserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> findAllUsers(String filter);

    User createUser(CreateUserDto createUserDto);

    Optional<User> findUserById(Long userId);

    User editUser(EditUserDto editUserDto);

    void deleteUserById(Long userId);

}
