package com.example.menu_planner.service;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String upload(@Valid MultipartFile image, Authentication authentication);
    public Resource download(@Valid String imageName, Authentication authentication);
}
