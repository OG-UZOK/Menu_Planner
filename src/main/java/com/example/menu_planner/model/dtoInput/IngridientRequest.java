package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record IngridientRequest(
        @NotNull(message = "is_liquid cannot be null")
        Boolean is_liquid,
        @NotBlank(message = "name cannot be empty")
        String name,
        @NotNull(message = "protein cannot be null")
        @PositiveOrZero(message = "protein must be greater than or equal to zero")
        Integer protein,
        @NotNull(message = "fat cannot be null")
        @PositiveOrZero(message = "fat must be greater than or equal to zero")
        Integer fat,
        @NotNull(message = "carbohydrates cannot be null")
        @PositiveOrZero(message = "carbohydrates must be greater than or equal to zero")
        Integer carbohydrates
) {
}
