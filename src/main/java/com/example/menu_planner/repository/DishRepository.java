package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID>, JpaSpecificationExecutor<Dish> {
    Page<Dish> findAll(Specification<Dish> spec, Pageable pageable);
}
