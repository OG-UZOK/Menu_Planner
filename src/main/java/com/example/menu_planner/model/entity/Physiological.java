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
@Table(name="physiological_characteristics")
public class Physiological {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name ="user_id")
    private UUID user_id;

    @Column(name ="gender")
    private String gender;

    @Column(name ="age")
    private Integer age;

    @Column(name ="height")
    private Integer height;

    @Column(name ="weight")
    private Integer weight;

    @Column(name ="daily_activity")
    private String daily_activity;

    @Column(name ="target")
    private String target;

    @Column(name ="body_mass_index")
    private Double body_mass_index;

    @Column(name ="calories")
    private Integer calories;

    @Column(name ="proteins")
    private Double proteins;

    @Column(name ="fats")
    private Double fats;

    @Column(name ="carbohydrates")
    private Double carbohydrates;
}
