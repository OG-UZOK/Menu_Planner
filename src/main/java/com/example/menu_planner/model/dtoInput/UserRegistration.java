package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record                                                                                                                                                   UserRegistration(
        @NotBlank(message = "User email cannot be empty")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email is not valid")
        String email,
        @Size(min = 6, message = "Password must be at least 6 character")
        @NotBlank(message = "User password cannot be empty")
        String password,

        @Size(min = 1, max = 30,  message = "User name size must be between 3 and 30 characters")
        @NotBlank(message = "User name cannot be empty")
        String name,
        @NotBlank(message = "User surname cannot be empty")
        @Size(min = 3, max = 30, message = "User surname size must be between 3 and 30 characters")
        String surname
) {
}
