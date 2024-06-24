package com.example.menu_planner.repository;

import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.DishesOnDate;
import com.example.menu_planner.model.entity.Ingridient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishesOnDateRepository extends JpaRepository<DishesOnDate, UUID> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM dishes_on_date WHERE date = :date AND user_id = :userId", nativeQuery = true)
    void deleteAllByDateAndUser_id(@Param("date") LocalDate date, @Param("userId") UUID userId);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM dishes_on_date WHERE date = :date AND user_id = :userId", nativeQuery = true)
    List<DishesOnDate> findAllByDateAndUser_id(@Param("date") LocalDate date, @Param("userId") UUID userId);
}

