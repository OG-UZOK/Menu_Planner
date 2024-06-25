package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.SavedDishes;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.*;
import com.example.menu_planner.service.SavedDishesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class SavedDishesServiceImpl implements SavedDishesService {

    private static final String UPLOAD_DIR = "uploads/";
    private final DishRepository dishRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;
    private final StepRepository stepRepository;
    private final IngridientRepository ingridientRepository;
    private final TypeOfMealRepository typeOfMealRepository;
    private final SavedDishesRepository savedDishesRepository;

    @SneakyThrows
    public String saveDishInList(@Valid UUID dish_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        Dish dish = dishRepository.findById(dish_id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));
         if(savedDishesRepository.findAllByDishIdAndUserId(dish_id, userId).isPresent()){
             throw new WrongData("Dish with id: " + dish_id + " already exist in saved list");
         }
         SavedDishes savedDishes = SavedDishes.of(null, userId, dish_id);
         savedDishesRepository.save(savedDishes);
         return "success";
    }

    @SneakyThrows
    public String deleteDishInList(@Valid UUID dish_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        Dish dish = dishRepository.findById(dish_id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));
        SavedDishes savedDishes = SavedDishes.of(null, userId, dish_id);
        savedDishesRepository.DeleteByDishIdAndUserId(dish_id, userId);
        return "success";
    }

    @SneakyThrows
    public List<Dish> getDishesInList(Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        List<Dish> savedList = null;
        savedList = savedDishesRepository.findDishesByUserId(userId);
        return savedList;
    }
}
