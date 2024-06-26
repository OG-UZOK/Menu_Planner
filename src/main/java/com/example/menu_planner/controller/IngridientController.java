package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.service.IngridientService;
import com.example.menu_planner.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingridient")
public class IngridientController {
    private final IngridientService ingridientService;
    @PostMapping("create")
    @ResponseBody
    public Ingridient createIngridient(@RequestBody IngridientRequest request, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return ingridientService.createIngridient(request, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @PutMapping("redact")
    @ResponseBody
    public Ingridient redactIngridient(@RequestBody IngridientRequest request, Authentication authentication,
                                       @NotNull(message = "Ingridient ID can not be empty")
                                       @RequestParam(value = "id") UUID ingridient_id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return ingridientService.redactIngridient(request, authentication, ingridient_id, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @DeleteMapping("delete")
    public String deleteIngridients(Authentication authentication,
                                       @NotNull(message = "Ingridient ID can not be empty")
                                       @RequestParam(value = "name") UUID ingridient_id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return ingridientService.deleteIngridient(ingridient_id, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @GetMapping("all")
    public List<Ingridient> getIngridients(Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return ingridientService.getingridients(authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }
}
