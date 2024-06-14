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
@Table(name="steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "step_id")
    UUID step_id;

    @Column(name = "number")
    private Integer number;

    @Column(name="dish_id")
    private UUID dish_id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "description")
    private String description;
}
