package com.example.menu_planner.model.dtoInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DayRequest(

        @NotNull(message = "Date cannot be null")
        LocalDate date,

        @NotNull(message = "List of dishes cannot be null")
        @Valid List<DishesOnDateRequest> dishes
) {
}
