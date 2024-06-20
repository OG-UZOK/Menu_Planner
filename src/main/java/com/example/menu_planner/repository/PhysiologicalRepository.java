package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Physiological;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhysiologicalRepository extends JpaRepository<Physiological, UUID> {
}
