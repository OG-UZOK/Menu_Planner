package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Physiological;
import com.example.menu_planner.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhysiologicalRepository extends JpaRepository<Physiological, UUID> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM physiological_characteristics WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUser_id(@Param("userId") UUID userId);
    @Query(value = "SELECT * FROM physiological_characteristics WHERE user_id = :userId", nativeQuery = true)
    Optional<Physiological> findByUserId(@Param("userId") UUID userId);
}
