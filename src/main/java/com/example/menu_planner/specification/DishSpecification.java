package com.example.menu_planner.specification;

import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.IngridientInDish;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class DishSpecification {

    public static Specification<Dish> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Dish> isOwnedByUser(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("userId"), userId);
        };
    }

    public static Specification<Dish> hasTags(List<UUID> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("tags").get("id").in(tags);
        };
    }



    public static Specification<Dish> minProteins(Double minProteins) {
        return (root, query, criteriaBuilder) -> {
            if (minProteins == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("proteins"), minProteins);
        };
    }

    public static Specification<Dish> maxProteins(Double maxProteins) {
        return (root, query, criteriaBuilder) -> {
            if (maxProteins == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("proteins"), maxProteins);
        };
    }

    public static Specification<Dish> minFats(Double minFats) {
        return (root, query, criteriaBuilder) -> {
            if (minFats == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fats"), minFats);
        };
    }

    public static Specification<Dish> maxFats(Double maxFats) {
        return (root, query, criteriaBuilder) -> {
            if (maxFats == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("fats"), maxFats);
        };
    }

    public static Specification<Dish> minCalories(Double minCalories) {
        return (root, query, criteriaBuilder) -> {
            if (minCalories == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("calories"), minCalories);
        };
    }

    public static Specification<Dish> maxCalories(Double maxCalories) {
        return (root, query, criteriaBuilder) -> {
            if (maxCalories == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("calories"), maxCalories);
        };
    }

    public static Specification<Dish> minCarbohydrates(Double minCarbohydrates) {
        return (root, query, criteriaBuilder) -> {
            if (minCarbohydrates == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("carbohydrates"), minCarbohydrates);
        };
    }

    public static Specification<Dish> maxCarbohydrates(Double maxCarbohydrates) {
        return (root, query, criteriaBuilder) -> {
            if (maxCarbohydrates == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("carbohydrates"), maxCarbohydrates);
        };
    }

    public static Specification<Dish> hasCategories(List<UUID> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("categories").get("id").in(categories);
        };
    }

    public static Specification<Dish> hasIngredients(List<UUID> ingredientIds) {
        return (root, query, criteriaBuilder) -> {
            if (ingredientIds == null || ingredientIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // Join with the ingredients in the dish through the intermediary entity
            Join<Dish, IngridientInDish> dishIngredients = root.join("ingridients");
            Join<IngridientInDish, Ingridient> ingredients = dishIngredients.join("ingridient");

            Predicate ingredientPredicate = ingredients.get("id").in(ingredientIds);

            // Ensure the dish contains all specified ingredients
            query.groupBy(root.get("id"));
            query.having(criteriaBuilder.equal(criteriaBuilder.countDistinct(ingredients.get("id")), ingredientIds.size()));

            return ingredientPredicate;
        };
    }

    public static Specification<Dish> hasNoIngredients(List<UUID> ingredientIds) {
        return (root, query, criteriaBuilder) -> {
            if (ingredientIds == null || ingredientIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Dish> subRoot = subquery.from(Dish.class);
            Join<Dish, IngridientInDish> subDishIngredients = subRoot.join("ingridients");
            Join<IngridientInDish, Ingridient> subIngredients = subDishIngredients.join("ingridient");

            subquery.select(subRoot.get("id"))
                    .where(subIngredients.get("id").in(ingredientIds));

            return criteriaBuilder.not(root.get("id").in(subquery));
        };
    }

    public static Specification<Dish> maxCookingTime(Double cookingTime) {
        return (root, query, criteriaBuilder) -> {
            if (cookingTime == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("cookingTime"), cookingTime);
        };
    }

    public static Specification<Dish> hasTypes(List<UUID> types) {
        return (root, query, criteriaBuilder) -> {
            if (types == null || types.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("types").get("id").in(types);
        };
    }
}