package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.Step;
import com.example.menu_planner.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
}
