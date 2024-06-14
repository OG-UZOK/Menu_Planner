package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.repository.TagRepository;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public Tag createTag(@Valid TagRequest tag, Authentication authentication) {
        if (tagRepository.findByName(tag.name()).isPresent()){
            throw new WrongData("This tag exists in DB");
        }
        Tag newTag = Tag.of(null, tag.name());
        return tagRepository.save(newTag);
    }
}
