package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface TagService {
    public Tag createTag(@Valid TagRequest tag, Authentication authentication);

    public Tag getTagById(@Valid UUID tag, Authentication authentication);

    public List<Tag> getTagAll(Authentication authentication);

}
