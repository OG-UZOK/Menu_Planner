package com.example.menu_planner.service;

import jakarta.validation.Valid;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StepService {
    public String upload(@Valid MultipartFile image, Authentication authentication);
    public Resource download(@Valid String imageName, Authentication authentication);
}
