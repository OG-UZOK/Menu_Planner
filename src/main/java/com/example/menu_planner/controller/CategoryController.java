package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.CategoryRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.CategoryService;
import com.example.menu_planner.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("create")
    @ResponseBody
    public Category registerUser(@RequestBody CategoryRequest request, Authentication authentication){
        return categoryService.createCategory(request, authentication);
    }
}
