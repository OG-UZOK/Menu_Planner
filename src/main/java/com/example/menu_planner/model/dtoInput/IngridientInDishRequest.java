package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record IngridientInDishRequest(

        @NotNull(message = "Ingridient ID cannot be null")
        UUID ingridient_id,
        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be greater than zero")
        Double amount,
        @NotBlank(message = "Unit cannot be empty")
        String unit,
        @NotNull(message = "gramm cannot be null")
        @PositiveOrZero(message = "gramm must be greater than or equal to zero")
        Integer gramm
) {
}
