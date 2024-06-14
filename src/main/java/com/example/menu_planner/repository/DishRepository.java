package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {
}
