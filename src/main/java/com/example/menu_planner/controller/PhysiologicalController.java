package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.PhysiologicalRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Physiological;
import com.example.menu_planner.service.PhysiologicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/physiological")
public class PhysiologicalController {
    private final PhysiologicalService service;
    @PostMapping("create")
    @ResponseBody
    public Physiological registerUser(@RequestBody PhysiologicalRequest request, Authentication authentication){
        return service.createPhysiological(request, authentication);
    }
}
