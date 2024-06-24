package com.example.menu_planner.model.dtoOutput;

import com.example.menu_planner.model.dtoInput.IngridientInDishRequest;
import com.example.menu_planner.model.dtoInput.StepRequest;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.Step;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DishResponse(
        @NotBlank(message = "Name cannot be empty")
        Dish dish,

        @NotBlank(message = "Save dish cannot be empty")
        Boolean savedDish,
        @NotEmpty(message = "Steps cannot be empty")
        @Valid List<Step> steps
) {
}
