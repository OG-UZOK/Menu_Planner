package com.example.menu_planner.model.dtoInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DishesOnWeekRequest(

        @NotNull(message = "Monday cannot be null")
        @Valid DayRequest monday,

        @NotNull(message = "Tuesday cannot be null")
        @Valid DayRequest tuesday,

        @NotNull(message = "Wednesday cannot be null")
        @Valid DayRequest wednesday,

        @NotNull(message = "Thursday cannot be null")
        @Valid DayRequest thursday,

        @NotNull(message = "Friday cannot be null")
        @Valid DayRequest friday,

        @NotNull(message = "Saturday cannot be null")
        @Valid DayRequest saturday,

        @NotNull(message = "Sunday cannot be null")
        @Valid DayRequest sunday
) {
}
