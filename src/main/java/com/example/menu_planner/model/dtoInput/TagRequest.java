package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TagRequest(
        @NotBlank(message = "name cannot be empty")
        String name
) {
}
