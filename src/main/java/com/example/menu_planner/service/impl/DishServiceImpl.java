package com.example.menu_planner.service.impl;

import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.repository.DishRepository;
import com.example.menu_planner.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private DishRepository dishRepository;

    public String createDish(Dish dish){
        return "agf";
    }
}
