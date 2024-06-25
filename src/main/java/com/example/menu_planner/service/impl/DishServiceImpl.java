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
import com.example.menu_planner.specification.DishSpecification;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    @Value("${external.folder.relative.path}")
    private String externalFolderRelativePath;

    private Path externalFolderPath;

    @PostConstruct
    public void init() {
        // Получаем путь к текущей директории
        Path currentRelativePath = Paths.get("").toAbsolutePath();
        log.info("Current absolute path is: " + currentRelativePath.toString());

        // Преобразуем относительный путь в абсолютный
        externalFolderPath = currentRelativePath.resolve(externalFolderRelativePath).normalize();
        log.info("External folder path is: " + externalFolderPath.toString());

        // Создаем директорию, если она не существует
        try {
            Files.createDirectories(externalFolderPath);
        } catch (Exception e) {
            throw new RuntimeException("Could not create external folder path", e);
        }
    }
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
    public Dish createDish(@Valid DishCreateRequest dish, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);

        Integer totalProteinDish = 0;
        Integer totalFatDish = 0;
        Integer totalCarbohydrateDish = 0;
        Integer totalCaloriesDish = 0;

        List<IngridientInDish> ingridientInDishList = new ArrayList<>();
        Set<Tag> tags = new HashSet<>();
        Set<Category> categories = new HashSet<>();
        Set<TypeOfMeal> types = new HashSet<>();

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

        if (dish.typeIds() != null) {
            for (UUID type : dish.typeIds()) {
                TypeOfMeal currentType = typeOfMealRepository.findById(type).orElseThrow(() -> new NotFoundException("Type of meal not found"));
                types.add(currentType);
            }
        }

        UUID idDish = UUID.randomUUID();


        if (dish.ingredient() != null) {
            for (IngridientInDishRequest ingridientRequest : dish.ingredient()) {
                Ingridient currentIngridient = ingridientRepository.findById(ingridientRequest.ingridient_id()).orElseThrow(() -> new NotFoundException("Ingridient not found"));
                totalProteinDish += (int) (currentIngridient.getProtein() * (ingridientRequest.amount() / 100));
                totalFatDish += (int) (currentIngridient.getFat() * (ingridientRequest.amount() / 100));
                totalCarbohydrateDish += (int) (currentIngridient.getCarbohydrates() * (ingridientRequest.amount() / 100));

                IngridientInDish ingridientInDish = IngridientInDish.of(null, idDish, currentIngridient, ingridientRequest.amount(), ingridientRequest.unit(), ingridientRequest.gramm());
                ingridientInDishList.add(ingridientInDish);
            }
        }

        totalProteinDish = totalProteinDish/dish.amountPortion();
        totalCarbohydrateDish = totalCarbohydrateDish/dish.amountPortion();
        totalFatDish = totalFatDish/dish.amountPortion();

        totalCaloriesDish = 4 * totalProteinDish + 4 * totalCarbohydrateDish + 9 * totalFatDish;

        LocalDate currentDate = LocalDate.now();


        if (dish.steps() != null) {
            for (StepRequest step : dish.steps()) {
                Step currentStep = Step.of(null, step.number(), idDish,  step.title(), step.image(), step.description());
                stepRepository.save(currentStep);
            }
        }

        String image = null;

        try {
            String name = dish.image() + ".png";
            Path filePath = externalFolderPath.resolve(name);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                image = dish.image();
            } else {
                throw new NotFoundException("Could not find or read file: " + dish.name());
            }
        } catch (NotFoundException ex){
            throw new RuntimeException("Could not find or read file: " + dish.name());
        }

        Dish newDish = Dish.of(idDish, dish.name(), currentDate,dish.amountPortion(), dish.cookingTime(), userId, categories, tags, types, ingridientInDishList,
                totalCaloriesDish, totalProteinDish, totalFatDish, totalCarbohydrateDish, image);

        // Сохранение блюда в репозитории
        newDish = dishRepository.save(newDish);


        savedDishesRepository.save(SavedDishes.of(null, userId, idDish));

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
        Set<TypeOfMeal> types = new HashSet<>();

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

        if (request.typeIds() != null) {
            for (UUID type : request.typeIds()) {
                TypeOfMeal currentType = typeOfMealRepository.findById(type).orElseThrow(() -> new NotFoundException("Type of meal not found"));
                types.add(currentType);
            }
        }

        // Retrieve and validate ingredients
        if (request.ingredient() != null) {
            for (IngridientInDishRequest ingredientRequest : request.ingredient()) {
                Ingridient currentIngredient = ingridientRepository.findById(ingredientRequest.ingridient_id()).orElseThrow(() -> new NotFoundException("Ingredient not found"));
                totalProteinDish += (int) (currentIngredient.getProtein() * (ingredientRequest.amount() / 100));
                totalFatDish += (int) (currentIngredient.getFat() * (ingredientRequest.amount() / 100));
                totalCarbohydrateDish += (int) (currentIngredient.getCarbohydrates() * (ingredientRequest.amount() / 100));

                IngridientInDish ingredientInDish = IngridientInDish.of(null, oldDish.getId(), currentIngredient, ingredientRequest.amount(), ingredientRequest.unit(), ingredientRequest.gramm());
                ingredientInDishList.add(ingredientInDish);
            }
        }

        totalProteinDish = totalProteinDish/request.amountPortion();
        totalCarbohydrateDish = totalCarbohydrateDish/request.amountPortion();
        totalFatDish = totalFatDish/request.amountPortion();

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

        String image = null;

        try {
            String name = request.image() + ".png";
            Path filePath = externalFolderPath.resolve(name);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                image = request.image();
            } else {
                throw new NotFoundException("Could not find or read file: " + request.name());
            }
        } catch (NotFoundException ex){
            throw new RuntimeException("Could not find or read file: " + request.name());
        }

        oldDish.getIngridients().clear();
        oldDish.getIngridients().addAll(ingredientInDishList);

        // Update the existing dish with the new details
        oldDish.setName(request.name());
        oldDish.setCategories(categories);
        oldDish.setTags(tags);
        oldDish.setTypes(types);
        oldDish.setCookingTime(request.cookingTime());
        oldDish.setAmountPortion(request.amountPortion());
        oldDish.setCalories(totalCaloriesDish);
        oldDish.setProteins(totalProteinDish);
        oldDish.setFats(totalFatDish);
        oldDish.setCarbohydrates(totalCarbohydrateDish);
        oldDish.setImage(image);

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

        savedDishesRepository.DeleteAllByDishId(dish_id);

        dishRepository.delete(dish);


        return "success";
    }

    @SneakyThrows
    public DishResponse getDishById(@Valid UUID dish_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Dish dish = dishRepository.findById(dish_id).orElseThrow(() -> new NotFoundException("Dish with the id not found"));

        List<Step> listSteps = stepRepository.findByDishId(dish.getId());

        Boolean savedDish = false;

        if (savedDishesRepository.findAllByDishIdAndUserId(dish_id, userId).isPresent()){
            savedDish = true;
        }

        DishResponse dishResponse = new DishResponse(dish, savedDish ,listSteps);
        return dishResponse;
    }

    @SneakyThrows
    public List<Dish> getDishAll(Authentication authentication, String name, Boolean myDishes,
                                 List<UUID> tags, List<UUID> categories,
                                 Double minProteins, Double maxProteins,
                                 Double minFats, Double maxFats,
                                 Double minCalories, Double maxCalories,
                                 Double minCarbohydrates, Double maxCarbohydrates,
                                 String sortField, String sortOrder, Double cookingTime,
                                 List<UUID> includeIngredientIds, List<UUID> excludeIngredientIds,
                                 List<UUID> types){
        UUID userId = null;
        if (myDishes != null && myDishes) {
            userId = tokenUtils.getUserIdFromAuthentication(authentication);
        }
        Specification<Dish> spec = Specification.where(DishSpecification.hasName(name))
                .and(DishSpecification.isOwnedByUser(userId))
                .and(DishSpecification.hasTags(tags))
                .and(DishSpecification.hasCategories(categories))
                .and(DishSpecification.minProteins(minProteins))
                .and(DishSpecification.maxProteins(maxProteins))
                .and(DishSpecification.minFats(minFats))
                .and(DishSpecification.maxFats(maxFats))
                .and(DishSpecification.minCalories(minCalories))
                .and(DishSpecification.maxCalories(maxCalories))
                .and(DishSpecification.minCarbohydrates(minCarbohydrates))
                .and(DishSpecification.maxCarbohydrates(maxCarbohydrates))
                .and(DishSpecification.hasIngredients(includeIngredientIds))
                .and(DishSpecification.hasNoIngredients(excludeIngredientIds))
                .and(DishSpecification.maxCookingTime(cookingTime))
                .and(DishSpecification.hasTypes(types));

        Sort sort = Sort.by(Sort.Direction.ASC, sortField != null ? sortField : "name");
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        }

        return dishRepository.findAll(spec, sort);

    }

    @Override
    public List<Dish> findDishesByIngredients(List<UUID> ingredientIds, Authentication authentication) {
        // Получение всех блюд
        List<Dish> allDishes = dishRepository.findAll();

        // Подсчет соответствия ингредиентов для каждого блюда
        Map<Dish, Long> dishMatchCount = new HashMap<>();
        for (Dish dish : allDishes) {
            long matchCount = dish.getIngridients().stream()
                    .filter(ingridientInDish -> ingredientIds.contains(ingridientInDish.getIngridient().getId()))
                    .count();
            dishMatchCount.put(dish, matchCount);
        }

        // Сортировка блюд по количеству соответствующих ингредиентов (от максимального к минимальному)
        List<Dish> sortedDishes = dishMatchCount.entrySet().stream()
                .sorted(Map.Entry.<Dish, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedDishes;
    }
}
