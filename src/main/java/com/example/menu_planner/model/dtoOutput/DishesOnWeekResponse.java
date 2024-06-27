package com.example.menu_planner.model.dtoOutput;

import com.example.menu_planner.model.dtoInput.DayRequest;
import com.example.menu_planner.model.entity.Dish;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DishesOnWeekResponse(
        @NotNull(message = "Monday cannot be null")
        @Valid List<Dish> monday,

        @NotNull(message = "Tuesday cannot be null")
        @Valid List<Dish> tuesday,

        @NotNull(message = "Wednesday cannot be null")
        @Valid List<Dish> wednesday,

        @NotNull(message = "Thursday cannot be null")
        @Valid List<Dish> thursday,

        @NotNull(message = "Friday cannot be null")
        @Valid List<Dish> friday,

        @NotNull(message = "Saturday cannot be null")
        @Valid List<Dish> saturday,

        @NotNull(message = "Sunday cannot be null")
        @Valid List<Dish> sunday,

        @NotNull(message = "CaloriesOnWeekResponse cannot be null")
        @NotBlank(message = "CaloriesOnWeekResponse cannot be null")
        CaloriesOnWeekResponse caloriesOnWeekResponse
) {
}
