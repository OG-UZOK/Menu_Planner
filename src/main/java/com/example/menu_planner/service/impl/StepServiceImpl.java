package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.repository.StepRepository;
import com.example.menu_planner.service.StepService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {
    private final StepRepository stepRepository;
    private static final String UPLOAD_DIR = "uploadsStep/";

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
            Path filePath = Paths.get(UPLOAD_DIR).resolve(imageName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Could not find or read file: " + imageName);
            }
        } catch (NotFoundException ex){
            throw new RuntimeException("Could not find or read file: " + imageName);
        }
    }
}
