package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.SavedDishes;
import com.example.menu_planner.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SavedDishesRepository  extends JpaRepository<SavedDishes,UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM saved_dishes WHERE dish_id = :dishId", nativeQuery = true)
    void DeleteAllByDishId(@Param("dishId") UUID dishId);

    @Query(value = "SELECT * FROM saved_dishes WHERE dish_id = :dishId AND user_id = :userId", nativeQuery = true)
    Optional<SavedDishes> findAllByDishIdAndUserId(@Param("dishId") UUID dishId, @Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM saved_dishes WHERE dish_id = :dishId AND user_id = :userId", nativeQuery = true)
    void DeleteByDishIdAndUserId(@Param("dishId") UUID dishId, @Param("userId") UUID userId);

    @Query("SELECT d FROM Dish d WHERE d.id IN (SELECT sd.dish_id FROM SavedDishes sd WHERE sd.user_id = :userId)")
    List<Dish> findDishesByUserId(@Param("userId") UUID userId);
}
