package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.Password;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.dtoOutput.UserProfileResponse;
import com.example.menu_planner.service.UserService;
import com.example.menu_planner.service.impl.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    @ResponseBody
    public JwtResponse registerUser(@RequestBody UserRegistration request){
        return userService.registrationUser(request);
    }

    @PostMapping("login")
    @ResponseBody
    public JwtResponse loginuser(@RequestBody UserLogin request){
        return userService.loginUser(request);
    }

    @GetMapping("profile")
    public UserProfileResponse getProfile(Authentication authentication,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return userService.getProfile(authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");

    }

    @PutMapping("profile")
    @ResponseBody
    public UserProfileResponse redactProfile(@RequestBody UserProfileResponse userProfileResponse, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return userService.redactProfile(userProfileResponse, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @PostMapping("change/password")
    @ResponseBody
    public Password changePassword(@RequestBody Password password, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return userService.changePassword(password, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @GetMapping("logout")
    public String logout(Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return userService.logout(authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");

    }

}
