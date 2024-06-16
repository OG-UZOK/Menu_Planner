package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record IngridientInDishRequest(

        @NotNull(message = "Ingridient ID cannot be null")
        UUID ingridient_id,
        @NotNull(message = "Amount cannot be null")
        @PositiveOrZero(message = "Amount must be greater than or equal to zero")
        Integer amount,
        @NotBlank(message = "Unit cannot be empty")
        String unit
) {
}
