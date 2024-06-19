package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.ForbiddenException;
import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.IngridientInDishRequest;
import com.example.menu_planner.model.dtoInput.StepRequest;
import com.example.menu_planner.model.dtoOutput.DishResponse;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private static final String UPLOAD_DIR = "uploadsStep/";
    private final DishRepository dishRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;
    private final StepRepository stepRepository;
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

        UUID idDish = UUID.randomUUID();


        if (dish.ingredient() != null) {
            for (IngridientInDishRequest ingridientRequest : dish.ingredient()) {
                Ingridient currentIngridient = ingridientRepository.findById(ingridientRequest.ingridient_id()).orElseThrow(() -> new NotFoundException("Ingridient not found"));
                totalProteinDish += currentIngridient.getProtein() * ingridientRequest.amount();
                totalFatDish += currentIngridient.getFat() * ingridientRequest.amount();
                totalCarbohydrateDish += currentIngridient.getCarbohydrates() * ingridientRequest.amount();

                IngridientInDish ingridientInDish = IngridientInDish.of(null, idDish, currentIngridient, ingridientRequest.amount(), ingridientRequest.unit());
                ingridientInDishList.add(ingridientInDish);
            }
        }


        totalCaloriesDish = 4 * totalProteinDish + 4 * totalCarbohydrateDish + 9 * totalFatDish;

        LocalDate currentDate = LocalDate.now();


        if (dish.steps() != null) {
            for (StepRequest step : dish.steps()) {
                Step currentStep = Step.of(null, step.number(), idDish,  step.title(), step.image(), step.description());
                stepRepository.save(currentStep);
            }
        }

        Dish newDish = Dish.of(idDish, dish.name(), currentDate, userId, categories, tags, ingridientInDishList,
                totalCaloriesDish, totalProteinDish, totalFatDish, totalCarbohydrateDish);

        // Сохранение блюда в репозитории
        newDish = dishRepository.save(newDish);


        return newDish;
    }

    @SneakyThrows
    public Dish redactDish(@Valid DishCreateRequest request, Authentication authentication, UUID id) {
        // Get user ID from authentication token
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        // Retrieve and validate the existing dish
        Dish oldDish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));

        // Check if the user has permission to update the dish
        if (!oldDish.getUserId().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        // Initialize nutritional values
        Integer totalProteinDish = 0;
        Integer totalFatDish = 0;
        Integer totalCarbohydrateDish = 0;
        Integer totalCaloriesDish = 0;

        // Initialize lists for ingredients, tags, and categories
        Set<Category> categories = new HashSet<>();
        Set<Tag> tags = new HashSet<>();
        List<IngridientInDish> ingredientInDishList = new ArrayList<>();

        // Retrieve and validate categories
        if (request.categoryIds() != null) {
            for (UUID category : request.categoryIds()) {
                Category currentCategory = categoryRepository.findById(category).orElseThrow(() -> new NotFoundException("Category not found"));
                categories.add(currentCategory);
            }
        }

        // Retrieve and validate tags
        if (request.tagIds() != null) {
            for (UUID tag : request.tagIds()) {
                Tag currentTag = tagRepository.findById(tag).orElseThrow(() -> new NotFoundException("Tag not found"));
                tags.add(currentTag);
            }
        }

        // Retrieve and validate ingredients
        if (request.ingredient() != null) {
            for (IngridientInDishRequest ingredientRequest : request.ingredient()) {
                Ingridient currentIngredient = ingridientRepository.findById(ingredientRequest.ingridient_id()).orElseThrow(() -> new NotFoundException("Ingredient not found"));
                totalProteinDish += currentIngredient.getProtein() * ingredientRequest.amount();
                totalFatDish += currentIngredient.getFat() * ingredientRequest.amount();
                totalCarbohydrateDish += currentIngredient.getCarbohydrates() * ingredientRequest.amount();

                IngridientInDish ingredientInDish = IngridientInDish.of(null, oldDish.getId(), currentIngredient, ingredientRequest.amount(), ingredientRequest.unit());
                ingredientInDishList.add(ingredientInDish);
            }
        }

        System.out.println(ingredientInDishList);

        // Calculate total calories
        totalCaloriesDish = 4 * totalProteinDish + 4 * totalCarbohydrateDish + 9 * totalFatDish;

        // Delete old steps (if any)
        List<Step> listOldSteps = stepRepository.findByDishId(oldDish.getId());
        if (listOldSteps != null) {
            for (Step oldStep : listOldSteps) {
                stepRepository.delete(oldStep);
            }
        }

        // Save new steps
        if (request.steps() != null) {
            for (StepRequest step : request.steps()) {
                Step currentStep = Step.of(null, step.number(), oldDish.getId(), step.title(), step.image(), step.description());
                stepRepository.save(currentStep);
            }
        }

        oldDish.getIngridients().clear();
        oldDish.getIngridients().addAll(ingredientInDishList);

        // Update the existing dish with the new details
        oldDish.setName(request.name());
        oldDish.setCategories(categories);
        oldDish.setTags(tags);
        oldDish.setCalories(totalCaloriesDish);
        oldDish.setProteins(totalProteinDish);
        oldDish.setFats(totalFatDish);
        oldDish.setCarbohydrates(totalCarbohydrateDish);

        return dishRepository.save(oldDish);
    }
    @SneakyThrows
    public String deleteDish(Authentication authentication, @Valid UUID dish_id) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Dish dish = dishRepository.findById(dish_id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));

        // Check if the user has permission to update the dish
        if (!dish.getUserId().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        // Delete old steps (if any)
        List<Step> listOldSteps = stepRepository.findByDishId(dish.getId());
        if (listOldSteps != null) {
            for (Step oldStep : listOldSteps) {
                stepRepository.delete(oldStep);
            }
        }

        dishRepository.delete(dish);


        return "success";
    }

    @SneakyThrows
    public DishResponse getDishById(@Valid UUID dish_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Dish dish = dishRepository.findById(dish_id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));

        List<Step> listSteps = stepRepository.findByDishId(dish.getId());

        DishResponse dishResponse = new DishResponse(dish, listSteps);
        return dishResponse;
    }
}
