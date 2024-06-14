package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
}
