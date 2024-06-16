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
@Table(name="dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name= "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name= "date", columnDefinition = "DATE")
    private LocalDate date;

    @JoinColumn(name= "user_id", referencedColumnName = "id")
    private UUID userId;

    @ManyToMany
    @JoinTable(
            name = "dishes_categories",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "dishes_tags",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngridientInDish> ingridients = new ArrayList<>();

    @Column(name="calories")
    private Integer calories;

    @Column(name="proteins")
    private Integer proteins;

    @Column(name="fats")
    private Integer fats;

    @Column(name="carbohydrates")
    private Integer carbohydrates;
}
