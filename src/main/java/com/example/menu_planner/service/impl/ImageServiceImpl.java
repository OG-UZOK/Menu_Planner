package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.repository.StepRepository;
import com.example.menu_planner.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private static final String UPLOAD_DIR = "uploads/";

    @SneakyThrows
    public String upload(@Valid MultipartFile image, Authentication authentication) {
        UUID idImage = UUID.randomUUID();
        String fileName = idImage.toString() + ".png";
        Path path = Paths.get(UPLOAD_DIR + fileName);

        log.info("Current working directory: " + System.getProperty("user.dir"));
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
    }

    @SneakyThrows
    public Resource download(@Valid String imageName, Authentication authentication){
        try {
            String name = imageName + ".png";
            Path filePath = Paths.get(UPLOAD_DIR).resolve(name);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Could not find or read file: " + name);
            }
        } catch (NotFoundException ex){
            throw new RuntimeException("Could not find or read file: " + imageName);
        }
    }
}