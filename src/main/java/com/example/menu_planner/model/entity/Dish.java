package com.example.menu_planner.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    UUID id;

    @Column(name= "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name= "date", columnDefinition = "DATE")
    private Date date;

    @JoinColumn(name= "user_id", referencedColumnName = "id")
    private UUID user_id;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
}
