package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DishCreateRequest(
        @NotBlank(message = "Name cannot be empty")
        @Size(max = 255, message = "Name cannot be longer than 255 characters")
        String name,

        Set<UUID> categoryIds,

        Set<UUID> tagIds,

        @NotEmpty(message = "Ingridients cannot be empty")
        List<IngridientInDishRequest> ingredient
) {
}
