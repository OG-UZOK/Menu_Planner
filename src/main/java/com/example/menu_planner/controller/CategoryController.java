package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.CategoryRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.CategoryService;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("create")
    @ResponseBody
    public Category createCategory(@RequestBody CategoryRequest request, Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return categoryService.createCategory(request, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @GetMapping()
    public Category getCategoryById(@Valid @NotNull(message="Id cant be empty") @RequestParam("id") UUID category_id,
                                   Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return categoryService.getCategoryById(category_id, authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    @GetMapping("/all")
    public List<Category> getCategoryAll(Authentication authentication, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token =  authorizationHeader.substring(7);
            return categoryService.getCategoryAll(authentication, token);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }


}
