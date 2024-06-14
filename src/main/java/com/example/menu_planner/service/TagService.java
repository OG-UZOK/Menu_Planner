package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface TagService {
    public Tag createTag(@Valid TagRequest tag, Authentication authentication);
}
