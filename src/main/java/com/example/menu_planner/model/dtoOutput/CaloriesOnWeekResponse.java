package com.example.menu_planner.model.dtoOutput;

import jakarta.validation.constraints.*;

public record CaloriesOnWeekResponse(
        @NotNull(message = "calories cannot be null")
        @Positive(message = "calories must be greater than zero")
        Integer calories,
        @NotNull(message = "proteins cannot be null")
        @Positive(message = "proteins must be greater than zero")
        Integer proteins,
        @NotNull(message = "fats cannot be null")
        @Positive(message = "fats must be greater than zero")
        Integer fats,
        @NotNull(message = "carbohydrates cannot be null")
        @Positive(message = "carbohydrates must be greater than zero")
        Integer carbohydrates
) {
}
