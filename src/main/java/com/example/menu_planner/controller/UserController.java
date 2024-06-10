package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.service.UserService;
import com.example.menu_planner.service.impl.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public JwtResponse registerUser(@RequestBody UserRegistration request){
        return userService.registrationUser(request);
    }

}
