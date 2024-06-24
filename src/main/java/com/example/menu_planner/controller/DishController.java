package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.DishCreateRequest;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.DishResponse;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.repository.DishRepository;
import com.example.menu_planner.service.DishService;
import com.example.menu_planner.service.SavedDishesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;
    private final SavedDishesService savedDishesService;

    @PostMapping("create")
    @ResponseBody
    public Dish createDish(@RequestBody DishCreateRequest request, Authentication authentication){
        return dishService.createDish(request, authentication);
    }

    @PostMapping("savedList/save")
    @ResponseBody
    public String saveDishInList(@RequestParam @NotNull(message = "dish_id can not be null") UUID dish_id, Authentication authentication) {
        return savedDishesService.saveDishInList(dish_id, authentication);
    }

    @DeleteMapping("savedList/delete")
    @ResponseBody
    public String deleteDishInList(@RequestParam @NotNull(message = "dish_id can not be null") UUID dish_id, Authentication authentication) {
        return savedDishesService.deleteDishInList(dish_id, authentication);
    }

    @GetMapping("savedList")
    @ResponseBody
    public List<Dish> getDishesInList(Authentication authentication) {
        return savedDishesService.getDishesInList(authentication);
    }


    @PutMapping("redact")
    @ResponseBody
    public Dish redactDish(@RequestBody DishCreateRequest request, Authentication authentication,@Valid @NotNull(message="Id cant be empty")
                                                                                @RequestParam("id") UUID id){
        return dishService.redactDish(request, authentication, id);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public String deleteDish(Authentication authentication,@Valid @NotNull(message="Id cant be empty")
    @RequestParam("id") UUID id){
        return dishService.deleteDish(authentication, id);
    }

    @GetMapping()
    public DishResponse getDishById(@Valid @NotNull(message="Id cant be empty") @RequestParam("id") UUID id, Authentication authentication){
        return dishService.getDishById(id, authentication);
    }

    @GetMapping("all")
    public List<Dish> getDishAll(Authentication authentication,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "myDishes", required = false) Boolean myDishes,
                                  @RequestParam(value = "tags", required = false) List<UUID> tags,
                                  @RequestParam(value = "categories", required = false) List<UUID> categories,
                                  @RequestParam(value = "minProteins", required = false) Double minProteins,
                                  @RequestParam(value = "maxProteins", required = false) Double maxProteins,
                                  @RequestParam(value = "minFats", required = false) Double minFats,
                                  @RequestParam(value = "maxFats", required = false) Double maxFats,
                                  @RequestParam(value = "minCalories", required = false) Double minCalories,
                                  @RequestParam(value = "maxCalories", required = false) Double maxCalories,
                                  @RequestParam(value = "minCarbohydrates", required = false) Double minCarbohydrates,
                                  @RequestParam(value = "maxCarbohydrates", required = false) Double maxCarbohydrates,
                                  @RequestParam(value = "sortField", required = false) String sortField,
                                  @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                  @RequestParam(value = "cookingTime", required = false) Double cookingTime,
                                  @RequestParam(value = "includeIngredients", required = false) List<UUID> includeIngredientIds,
                                  @RequestParam(value = "excludeIngredients", required = false) List<UUID> excludeIngredientIds,
                                  @RequestParam(value = "types", required = false) List<UUID> types) {
        return dishService.getDishAll(authentication, name, myDishes, tags, categories,
                minProteins, maxProteins, minFats, maxFats,
                minCalories, maxCalories, minCarbohydrates, maxCarbohydrates,
                sortField, sortOrder, cookingTime,includeIngredientIds, excludeIngredientIds, types );
    }

    @PostMapping("/findByIngredients")
    public List<Dish> findDishesByIngredients(@Valid @NotEmpty(message = "Ids cannot be empty") @RequestBody List<UUID> ingredientIds, Authentication authentication) {
        return dishService.findDishesByIngredients(ingredientIds, authentication);
    }
}
