package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.Password;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.DishResponse;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.dtoOutput.UserProfileResponse;
import com.example.menu_planner.model.entity.Dish;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface DishService {
    public Dish createDish(@Valid DishCreateRequest dish, Authentication authentication, String token);

    public Dish redactDish(@Valid DishCreateRequest request, Authentication authentication, UUID id, String token);

    public String deleteDish(Authentication authentication,@Valid UUID dish_id, String token);

    public DishResponse getDishById(@Valid UUID id, Authentication authentication, String token);

    public List<Dish> findDishesByIngredients(List<UUID> ingredientIds, Authentication authentication, String token);

    public Page<Dish> getDishAll(Authentication authentication, String name, Boolean myDishes,
                                 List<UUID> tags, List<UUID> categories,
                                 Double minProteins, Double maxProteins,
                                 Double minFats, Double maxFats,
                                 Double minCalories, Double maxCalories,
                                 Double minCarbohydrates, Double maxCarbohydrates,
                                 String sortField, String sortOrder, Double cookingTime,
                                 List<UUID> includeIngredientIds, List<UUID> excludeIngredientIds,
                                 List<UUID> types, String token, Pageable pageable);
}
