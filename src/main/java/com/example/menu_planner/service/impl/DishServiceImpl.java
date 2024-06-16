package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.IngridientInDishRequest;
import com.example.menu_planner.model.entity.*;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.*;
import com.example.menu_planner.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;
    private final IngridientRepository ingridientRepository;
    private final IngridientInDishRepository ingridientInDishRepository;

    @SneakyThrows
    public Dish createDish(@Valid DishCreateRequest dish, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Integer totalProteinDish = 0;
        Integer totalFatDish = 0;
        Integer totalCarbohydrateDish = 0;
        Integer totalCaloriesDish = 0;

        List<IngridientInDish> ingridientInDishList = new ArrayList<>();
        Set<Tag> tags = new HashSet<>();
        Set<Category> categories = new HashSet<>();

        if (dish.tagIds() != null) {
            for (UUID tag : dish.tagIds()) {
                Tag currentTag = tagRepository.findById(tag).orElseThrow(() -> new NotFoundException("Tag not found"));
                tags.add(currentTag);
            }
        }

        if (dish.categoryIds() != null) {
            for (UUID category : dish.categoryIds()) {
                Category currentCategory = categoryRepository.findById(category).orElseThrow(() -> new NotFoundException("Category not found"));
                categories.add(currentCategory);
            }
        }

        if (dish.ingredient() != null) {
            for (IngridientInDishRequest ingridientRequest : dish.ingredient()) {
                Ingridient currentIngridient = ingridientRepository.findById(ingridientRequest.ingridient_id()).orElseThrow(() -> new NotFoundException("Ingridient not found"));
                totalProteinDish += currentIngridient.getProtein() * ingridientRequest.amount();
                totalFatDish += currentIngridient.getFat() * ingridientRequest.amount();
                totalCarbohydrateDish += currentIngridient.getCarbohydrates() * ingridientRequest.amount();

                IngridientInDish ingridientInDish = IngridientInDish.of(null, null, currentIngridient, ingridientRequest.amount(), ingridientRequest.unit());
                ingridientInDishList.add(ingridientInDish);
            }
        }

        totalCaloriesDish = 4 * totalProteinDish + 4 * totalCarbohydrateDish + 9 * totalFatDish;

        LocalDate currentDate = LocalDate.now();

        Dish newDish = Dish.of(null, dish.name(), currentDate, userId, categories, tags, new ArrayList<>(), totalCaloriesDish, totalProteinDish, totalFatDish, totalCarbohydrateDish);
        newDish = dishRepository.save(newDish);

        for (IngridientInDish ingridientInDish : ingridientInDishList) {
            ingridientInDish.setDish(newDish);
            ingridientInDishRepository.save(ingridientInDish);
        }

        return newDish;
    }
}
