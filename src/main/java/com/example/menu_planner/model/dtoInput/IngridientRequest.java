package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngridientRequest(
        @NotBlank(message = "is_liquid cannot be empty")
        Boolean is_liquid,
        @NotBlank(message = "name cannot be empty")
        String name,
        @Size(min=0)
        @NotBlank(message = "protein cannot be empty")
        Integer protein,
        @Size(min=0)
        @NotBlank(message = "fat cannot be empty")
        Integer fat,
        @Size(min=0)
        @NotBlank(message = "carbohydrates cannot be empty")
        Integer carbohydrates

) {
}
