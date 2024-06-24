package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @PostMapping("create")
    @ResponseBody
    public Tag registerUser(@RequestBody TagRequest request, Authentication authentication){
        return tagService.createTag(request, authentication);
    }

    @GetMapping()
    public Tag getTagById(@Valid @NotNull(message="Id cant be empty") @RequestParam("id") UUID tag_id,
                                    Authentication authentication){
        return tagService.getTagById(tag_id, authentication);
    }

    @GetMapping("/all")
    public List<Tag> getTagAll(Authentication authentication){
        return tagService.getTagAll(authentication);
    }
}
