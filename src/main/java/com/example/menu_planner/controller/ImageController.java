package com.example.menu_planner.controller;

import com.example.menu_planner.service.ImageService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService service;
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@Valid @NotNull @RequestParam("image") MultipartFile image, Authentication authentication){
        return service.upload(image, authentication);
    }

    @GetMapping("/download/{name}")
    @ResponseBody
    public ResponseEntity<Resource> upload(@Valid @NotBlank(message = "Image ID cannot be null")
                                               @PathVariable("name") String imageName, Authentication authentication){
         Resource resource = service.download(imageName, authentication);
         return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
