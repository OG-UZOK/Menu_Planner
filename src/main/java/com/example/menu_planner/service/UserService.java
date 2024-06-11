package com.example.menu_planner.service;

import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import jakarta.validation.Valid;

public interface UserService {
    JwtResponse loginUser(@Valid UserLogin userLogin);
    JwtResponse registrationUser(@Valid UserRegistration userRegistration);
}
