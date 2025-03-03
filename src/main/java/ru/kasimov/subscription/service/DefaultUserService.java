package ru.kasimov.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kasimov.subscription.domain.mapper.UserMapper;
import ru.kasimov.subscription.domain.model.User;
import ru.kasimov.subscription.domain.dto.CreateUserDto;
import ru.kasimov.subscription.domain.dto.EditUserDto;
import ru.kasimov.subscription.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @EntityGraph(attributePaths = "subscriptions")
    @Transactional(readOnly = true)
    public Iterable<User> findAllUsers(String filter) {
        try {
            if (filter != null && !filter.isBlank()) {
                log.info("Finding all users with filter by their name: {}", filter);
                return this.userRepository.findAllByUsernameLikeIgnoreCase("%" + filter + "%");
            } else {
                log.info("Finding all users without filter by their name");
                return this.userRepository.findAll();
            }
        } catch (Exception e) {
            log.error("Failed to find all users. Exception: {}", e.getMessage());
            throw new RuntimeException("Failed to find all users");
        }

    }

    @Override
    @Transactional
    public User createUser(CreateUserDto createUserDto) {
        log.info("Creating user: {}", createUserDto.username());

        try {
            User user = UserMapper.INSTANCE.createUserDtoToUser(createUserDto);
            return this.userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to create user: {}. Exception: {}", createUserDto.username(), e.getMessage());
            throw new RuntimeException("Failed to create user: %s".formatted(createUserDto.username()));
        }
    }

    @Override
    @EntityGraph(attributePaths = "subscriptions")
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        log.info("Finding user with id: {}", userId);

        try {
            return this.userRepository.findById(userId);
        } catch (Exception e) {
            log.error("Failed to find user by id: {}. Exception: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to find user by id: %d".formatted(userId));
        }
    }

    @Override
    @Transactional
    public User editUser(EditUserDto editUserDto) {
        log.info("Editing user with id: {}", editUserDto.id());

        try {
            return this.userRepository.findById(editUserDto.id())
                    .map(existedUser -> {
                        existedUser.setUsername(editUserDto.username());
                        existedUser.setEmail(editUserDto.email());
                        return this.userRepository.save(existedUser);
                    })
                    .orElseThrow(() -> {
                        log.error("Failed to edit user : {} with id: {}", editUserDto.username(), editUserDto.id());
                        return new NoSuchElementException("User not found");
                    });
        } catch (Exception e) {
            log.error("Failed to edit user with id: {}. Exception: {}", editUserDto.id(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        log.info("Deleting user with id: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User with id {} not found", userId);
            throw new NoSuchElementException("User not found with id: " + userId);
        }

        this.userRepository.deleteById(userId);
    }
}
