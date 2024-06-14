package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.service.IngridientService;
import com.example.menu_planner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingridient")
public class IngridientController {
    private final IngridientService ingridientService;
    @PostMapping("create")
    @ResponseBody
    public Ingridient registerUser(@RequestBody IngridientRequest request, Authentication authentication){
        return ingridientService.createIngridient(request, authentication);
    }
}
