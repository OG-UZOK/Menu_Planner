package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.Password;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.dtoOutput.UserProfileResponse;
import com.example.menu_planner.model.entity.Dish;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface DishService {
    public String createDish(Dish dish);
}
