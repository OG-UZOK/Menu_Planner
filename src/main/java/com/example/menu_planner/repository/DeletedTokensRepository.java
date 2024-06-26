package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.DeletedTokens;
import com.example.menu_planner.model.entity.DishesOnDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeletedTokensRepository extends JpaRepository<DeletedTokens, String> {
}
