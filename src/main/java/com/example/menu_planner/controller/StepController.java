package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.StepService;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/step")
public class StepController {
    private final StepService service;
    @PostMapping("/image/upload")
    @ResponseBody
    public String upload(@Valid @NotNull @RequestParam("image") MultipartFile image, Authentication authentication){
        return service.upload(image, authentication);
    }
}
