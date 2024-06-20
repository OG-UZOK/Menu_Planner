package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.model.entity.TypeOfMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeOfMealRepository extends JpaRepository<TypeOfMeal, UUID> {
    Optional<TypeOfMeal> findByName(String name);
}
