package com.example.menu_planner.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "dishes_ingridients")
public class IngridientInDish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @JoinColumn(name = "dish_id")
    private UUID dish_id;

    @ManyToOne
    @JoinColumn(name = "ingridient_id")
    private Ingridient ingridient;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit")
    private String unit;

    @Column(name="gramm")
    private Integer gramm;
}
