package com.example.menu_planner.model.dtoOutput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserProfileResponse(
        @NotBlank(message = "User email cannot be empty")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email is not valid")
        String email,
        @Size(min = 1, max = 30,  message = "User name size must be between 3 and 30 characters")
        @NotBlank(message = "User name cannot be empty")
        String name,

        @NotBlank(message = "User surname cannot be empty")
        @Size(min = 3, max = 30, message = "User surname size must be between 3 and 3")
        String surname
) {
}
