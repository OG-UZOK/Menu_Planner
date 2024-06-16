package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.repository.DishRepository;
import com.example.menu_planner.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @PostMapping("create")
    @ResponseBody
    public Dish createDish(@RequestBody DishCreateRequest request, Authentication authentication){
        return dishService.createDish(request, authentication);
    }
}
