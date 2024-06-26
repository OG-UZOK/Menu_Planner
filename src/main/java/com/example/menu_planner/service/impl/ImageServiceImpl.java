package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.UnauthorizedException;
import com.example.menu_planner.repository.DeletedTokensRepository;
import com.example.menu_planner.repository.StepRepository;
import com.example.menu_planner.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${external.folder.relative.path}")
    private String externalFolderRelativePath;
    private final DeletedTokensRepository deletedTokensRepository;


    private Path externalFolderPath;

    @PostConstruct
    public void init() {
        // Получаем путь к текущей директории
        Path currentRelativePath = Paths.get("").toAbsolutePath();
        log.info("Current absolute path is: " + currentRelativePath.toString());

        // Преобразуем относительный путь в абсолютный
        externalFolderPath = currentRelativePath.resolve(externalFolderRelativePath).normalize();
        log.info("External folder path is: " + externalFolderPath.toString());

        // Создаем директорию, если она не существует
        try {
            Files.createDirectories(externalFolderPath);
        } catch (Exception e) {
            throw new RuntimeException("Could not create external folder path", e);
        }
    }

    @SneakyThrows
    public String upload(@Valid MultipartFile image, Authentication authentication, String token) {
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        UUID idImage = UUID.randomUUID();
        String fileName = idImage.toString() + ".png";
        Path path = externalFolderPath.resolve(fileName);

        log.info("Current working directory: " + System.getProperty("user.dir"));
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
    }

    @SneakyThrows
    public Resource download(@Valid String imageName, Authentication authentication, String token){
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        try {
            String name = imageName + ".png";
            Path filePath = externalFolderPath.resolve(name);
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
