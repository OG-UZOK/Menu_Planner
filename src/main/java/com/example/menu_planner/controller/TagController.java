package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
