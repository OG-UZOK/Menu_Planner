package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.CategoryRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public Category createCategory(@Valid CategoryRequest category, Authentication authentication);

    public Category getCategoryById(@Valid UUID category_id,Authentication authentication);

    public List<Category> getCategoryAll(Authentication authentication);
}
