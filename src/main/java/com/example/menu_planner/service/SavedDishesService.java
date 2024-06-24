package com.example.menu_planner.service;

import com.example.menu_planner.model.entity.Dish;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface SavedDishesService {

    public String saveDishInList(@Valid UUID dish_id, Authentication authentication);

    public String deleteDishInList(@Valid UUID dish_id, Authentication authentication);

    public List<Dish> getDishesInList(Authentication authentication);




}
