package com.example.menu_planner.model.dtoInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DishCreateRequest(
        @NotBlank(message = "Name cannot be empty")
        @Size(max = 255, message = "Name cannot be longer than 255 characters")
        String name,

        @NotNull(message = "Amount of portion cannot be null")
        @Positive(message = "Amount of portion must be greater than zero")
        Integer amountPortion,

        @NotNull(message = "Cooking time cannot be null")
        @Positive(message = "Cooking time must be greater than zero")
        Integer cookingTime,

        Set<UUID> categoryIds,

        Set<UUID> tagIds,

        Set<UUID> typeIds,

        @NotEmpty(message = "Ingridients cannot be empty")
        @Valid List<IngridientInDishRequest> ingredient,

        @NotEmpty(message = "Steps cannot be empty")
        @Valid List<StepRequest> steps
) {
}
