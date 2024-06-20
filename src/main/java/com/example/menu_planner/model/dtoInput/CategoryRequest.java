package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "name cannot be empty")
        String name
) {
}
