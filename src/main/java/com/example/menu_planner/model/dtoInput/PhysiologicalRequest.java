package com.example.menu_planner.model.dtoInput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record PhysiologicalRequest(
        @NotBlank(message = "Gender cannot be empty")
        @Size(max = 20, message = "Gender cannot be longer than 20 characters")
        @Pattern(regexp = "Male|Female", message = "Gender can only be 'Male' or 'Female'")
        String gender,

        @NotNull(message = "Age cannot be null")
        @Positive(message = "Age must be greater than zero")
        @Max(value = 100, message = "Age cannot be more than 100")
        Integer age,

        @NotNull(message = "Height cannot be null")
        @Positive(message = "Height must be greater than zero")
        @Max(value = 300, message = "Height cannot be more than 300")
        Integer height,

        @NotNull(message = "Weight cannot be null")
        @Positive(message = "Weight must be greater than zero")
        @Max(value = 700, message = "Weight cannot be more than 700")
        Integer weight,

        @NotNull(message = "Daily activity cannot be null")
        @Positive(message = "Daily activity must be greater than zero")
        @Max(value = 5, message = "Daily activity cannot be more than 5")
        Integer dailyActivity,

        @NotNull(message = "Target cannot be null")
        @Positive(message = "Target must be greater than zero")
        @Max(value = 3, message = "Target cannot be more than 3")
        Integer target
) {
}
