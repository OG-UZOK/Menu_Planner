package com.example.menu_planner.model.dtoInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record StepRequest(
        @NotNull(message = "number cannot be null")
        @Positive(message = "number must be greater than zero")
        Integer number,

        @NotBlank(message = "title cannot be empty")
        String title,

        @NotBlank(message = "image cannot be empty")
        String image,

        @NotBlank(message = "description cannot be empty")
        String description
) {
}
