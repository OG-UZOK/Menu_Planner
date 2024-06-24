package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record IngridientRequest(
        @NotBlank(message = "name cannot be empty")
        String name,
        @NotNull(message = "protein cannot be null")
        @PositiveOrZero(message = "protein must be greater than or equal to zero")
        Double protein,
        @NotNull(message = "fat cannot be null")
        @PositiveOrZero(message = "fat must be greater than or equal to zero")
        Double fat,
        @NotNull(message = "carbohydrates cannot be null")
        @PositiveOrZero(message = "carbohydrates must be greater than or equal to zero")
        Double carbohydrates,

        String image
) {
}
