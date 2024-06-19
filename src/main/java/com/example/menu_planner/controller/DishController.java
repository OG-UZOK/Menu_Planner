package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.DishResponse;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.repository.DishRepository;
import com.example.menu_planner.service.DishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @PostMapping("create")
    @ResponseBody
    public Dish createDish(@RequestBody DishCreateRequest request, Authentication authentication){
        System.out.println("request");
        return dishService.createDish(request, authentication);
    }

    @PostMapping("redact")
    @ResponseBody
    public Dish redactDish(@RequestBody DishCreateRequest request, Authentication authentication,@Valid @NotNull(message="Id cant be empty")
                                                                                @RequestParam("id") UUID id){
        return dishService.redactDish(request, authentication, id);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public String deleteDish(Authentication authentication,@Valid @NotNull(message="Id cant be empty")
    @RequestParam("id") UUID id){
        return dishService.deleteDish(authentication, id);
    }

    @GetMapping()
    public DishResponse getDishById(@Valid @NotNull(message="Id cant be empty") @RequestParam("id") UUID id, Authentication authentication){
        return dishService.getDishById(id, authentication);
    }

    @GetMapping("all")
    public List<Dish> getDishById(Authentication authentication, @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "myDishes", required = false) Boolean myDishes){
        return dishService.getDishAll(authentication, name, myDishes);
    }
}
