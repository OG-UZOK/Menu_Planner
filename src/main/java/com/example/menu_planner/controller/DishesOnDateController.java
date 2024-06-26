package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.DishesOnWeekRequest;
import com.example.menu_planner.model.dtoOutput.CaloriesOnWeekResponse;
import com.example.menu_planner.model.dtoOutput.DishResponse;
import com.example.menu_planner.model.dtoOutput.DishesOnWeekResponse;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.repository.DishesOnDateRepository;
import com.example.menu_planner.service.DishesOnDateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dishesOnDate")
public class DishesOnDateController {
    private final DishesOnDateService dishesOnDateService;

    @PostMapping("create")
    @ResponseBody
    public CaloriesOnWeekResponse createPlanOnWeek(@RequestBody DishesOnWeekRequest request, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return dishesOnDateService.createPlanOnWeek(request, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @PutMapping("redact")
    @ResponseBody
    public CaloriesOnWeekResponse redactPlanOnWeek(@RequestBody DishesOnWeekRequest request, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return dishesOnDateService.redactPlanOnWeek(request, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @GetMapping()
    public DishesOnWeekResponse getDishesOnWeek(
            @RequestParam("startDate") @NotNull(message = "startDate cannot be empty") LocalDate startDate,
            @RequestParam("endDate") @NotNull(message = "endDate cannot be empty") LocalDate endDate,
            Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return dishesOnDateService.getDishesOnWeek(startDate, endDate, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

}
