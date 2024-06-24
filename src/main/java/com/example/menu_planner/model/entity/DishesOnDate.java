package com.example.menu_planner.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name="dishes_on_date")
public class DishesOnDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name= "user_id")
    private UUID user_id;

    @Column(name= "dish_id")
    private UUID dish_id;

    @Column(name= "date")
    private LocalDate date;

    @Column(name="number")
    private Integer number;
}
