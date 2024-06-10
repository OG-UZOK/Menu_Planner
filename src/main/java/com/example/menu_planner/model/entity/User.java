package com.example.menu_planner.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(name= "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name= "surname", nullable = false, columnDefinition = "VARCHAR(255)")
    private String surname;

    @Column(name= "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name= "email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

}
