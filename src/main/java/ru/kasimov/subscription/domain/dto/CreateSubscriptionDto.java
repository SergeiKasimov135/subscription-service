package ru.kasimov.subscription.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSubscriptionDto(
        @NotNull
        @NotBlank
        String name
) {
}
