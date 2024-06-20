package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.CategoryRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface CategoryService {
    public Category createCategory(@Valid CategoryRequest category, Authentication authentication);
}
