package ru.kasimov.subscription.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotNull
        @NotBlank
        String username,

        @Email
        String email
) {
}
