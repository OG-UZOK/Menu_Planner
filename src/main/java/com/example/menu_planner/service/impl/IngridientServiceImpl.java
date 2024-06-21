package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.ForbiddenException;
import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.IngridientRepository;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.IngridientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class IngridientServiceImpl implements IngridientService {
    private final IngridientRepository ingridientRepository;
    private final JwtTokenUtils tokenUtils;
    private final UserRepository userRepository;

    @SneakyThrows
    public Ingridient createIngridient(@Valid IngridientRequest ingridient, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        if (ingridientRepository.findByName(ingridient.name()).isPresent()){
            throw new WrongData("This name exists in DB");
        }
        Ingridient newIngridient = Ingridient.of(null, userId, ingridient.name(), ingridient.is_liquid(), ingridient.protein(), ingridient.fat()
        , ingridient.carbohydrates());

        return ingridientRepository.save(newIngridient);
    }

    @SneakyThrows
    public Ingridient redactIngridient(@Valid IngridientRequest request, Authentication authentication,
                                       @Valid UUID ingridient_id){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Ingridient ingridient = ingridientRepository.findById(ingridient_id).orElseThrow(() -> new NotFoundException("Ingridient with the id not found"));

        if (!ingridient.getUser_id().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        if (ingridientRepository.findByName(request.name()).isPresent()){
            throw new WrongData("This name exists in DB");
        }

        Ingridient newIngridient = Ingridient.of(null, userId, request.name(), request.is_liquid(), request.protein(), request.fat()
                , request.carbohydrates());

        return ingridientRepository.save(newIngridient);
    }

    @SneakyThrows
    public String deleteIngridient(@Valid UUID ingridient_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Ingridient ingridient = ingridientRepository.findById(ingridient_id).orElseThrow(() -> new NotFoundException("Ingridient with the id not found"));

        if (!ingridient.getUser_id().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        ingridientRepository.deleteById(ingridient_id);
        return "success";
    }
}
