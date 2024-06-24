package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.DishesOnWeekRequest;
import com.example.menu_planner.model.dtoOutput.CaloriesOnWeekResponse;
import com.example.menu_planner.model.dtoOutput.DishesOnWeekResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface DishesOnDateService {

    public CaloriesOnWeekResponse createPlanOnWeek(@Valid DishesOnWeekRequest request, Authentication authentication);

    public CaloriesOnWeekResponse redactPlanOnWeek(@Valid DishesOnWeekRequest request, Authentication authentication);

    public DishesOnWeekResponse getDishesOnWeek(@Valid LocalDate startDate, @Valid LocalDate endDate, Authentication authentication);
}
