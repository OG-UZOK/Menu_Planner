package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StepRepository extends JpaRepository<Step, UUID> {
    @Query(value = "SELECT * FROM steps WHERE dish_id = :dishId", nativeQuery = true)
    List<Step> findByDishId(@Param("dishId") UUID dishId);
}
