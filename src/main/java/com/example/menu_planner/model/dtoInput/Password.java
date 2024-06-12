package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Password(
        @Size(min = 6, message = "Password must be at least 6 character")
        @NotBlank(message = "User password cannot be empty")
        String password
) {
}
