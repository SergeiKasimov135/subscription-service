package ru.kasimov.subscription.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.subscription.domain.dto.CreateUserDto;
import ru.kasimov.subscription.domain.dto.EditUserDto;
import ru.kasimov.subscription.domain.model.User;
import ru.kasimov.subscription.service.SubscriptionService;
import ru.kasimov.subscription.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAllUsers(@RequestParam(name = "filter", required = false) String filter) {
        return ResponseEntity.ok(this.userService.findAllUsers(filter));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return ResponseEntity.ok(this.userService.createUser(createUserDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return this.userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody @Valid EditUserDto editUserDto) {
        if (!id.equals(editUserDto.id())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.userService.editUser(editUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
