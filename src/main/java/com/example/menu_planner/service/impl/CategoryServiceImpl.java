package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.ForbiddenException;
import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.CategoryRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.CategoryRepository;
import com.example.menu_planner.repository.TagRepository;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.CategoryService;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtTokenUtils tokenUtils;
    private final UserRepository userRepository;

    @SneakyThrows
    public Category createCategory(@Valid CategoryRequest category, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        System.out.println(!Objects.equals(user.getRole(), "ADMIN"));

        if (!Objects.equals(user.getRole(), "ADMIN")) {
            throw new ForbiddenException("you don't have sufficient rights");
        }
        System.out.println("987654321");
        if (categoryRepository.findByName(category.name()).isPresent()){
            throw new WrongData("This category exists in DB");
        }
        Category newCategory = Category.of(null, category.name());

        return categoryRepository.save(newCategory);
    }

    @SneakyThrows
    public Category getCategoryById(@Valid UUID category_id,Authentication authentication){
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new NotFoundException("Category not found"));
        return category;
    }

    @SneakyThrows
    public List<Category> getCategoryAll(Authentication authentication){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
