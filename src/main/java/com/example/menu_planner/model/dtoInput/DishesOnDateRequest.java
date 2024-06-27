package com.example.menu_planner.model.dtoInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DishesOnDateRequest(
        @NotNull(message = "Dish id cannot be null")
        UUID dish_id,

        @NotNull(message = "Number time cannot be null")
        @Positive(message = "Number time must be greater than zero")
        Integer number
) {
}
