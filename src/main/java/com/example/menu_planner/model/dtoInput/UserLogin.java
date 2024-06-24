package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLogin(
        @NotBlank(message = "User email cannot be empty")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email is not valid")
        String email,
        @Size(min = 6, message = "Password must be at least 6 character")
        @NotBlank(message = "User password cannot be empty")
        String password
) {
}
