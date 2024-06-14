package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StepRepository extends JpaRepository<Step, UUID> {
}
