package com.example.menu_planner.service;

import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.model.dtoInput.Password;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.dtoOutput.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface UserService {
    JwtResponse loginUser(@Valid UserLogin userLogin);
    JwtResponse registrationUser(@Valid UserRegistration userRegistration);
    UserProfileResponse getProfile(Authentication authentication, String token);
    UserProfileResponse redactProfile(@Valid  UserProfileResponse userProfileResponse, Authentication authentication, String token);
    Password changePassword(@Valid Password password, Authentication authentication, String token);

    String logout(Authentication authentication,  String token);
}
