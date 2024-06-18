package com.example.menu_planner.service;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StepService {
    public UUID upload(@Valid MultipartFile image, Authentication authentication);
}
