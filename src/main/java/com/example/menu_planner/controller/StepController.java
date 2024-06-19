package com.example.menu_planner.controller;

import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.service.StepService;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/image/download/{name}")
    @ResponseBody
    public ResponseEntity<Resource> upload(@Valid @NotBlank @PathVariable("name") String imageName, Authentication authentication){
         Resource resource = service.download(imageName, authentication);
         return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
