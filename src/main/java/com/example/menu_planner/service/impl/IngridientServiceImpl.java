package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.ForbiddenException;
import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.IngridientRepository;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.IngridientService;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class IngridientServiceImpl implements IngridientService {
    private final IngridientRepository ingridientRepository;
    private final JwtTokenUtils tokenUtils;
    private final UserRepository userRepository;
    @Value("${external.folder.relative.path}")
    private String externalFolderRelativePath;

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
    public Ingridient createIngridient(@Valid IngridientRequest ingridient, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        if (ingridientRepository.findByName(ingridient.name()).isPresent()){
            throw new WrongData("This name exists in DB");
        }

        String image = null;
        if (ingridient.image() != null){
            try {
                String name = ingridient.image() + ".png";
                Path filePath = externalFolderPath.resolve(name);
                Resource resource = new UrlResource(filePath.toUri());

                if (resource.exists() || resource.isReadable()) {
                    image = ingridient.image();
                } else {
                    throw new NotFoundException("Could not find or read file: " + ingridient.name());
                }
            } catch (NotFoundException ex){
                throw new RuntimeException("Could not find or read file: " + ingridient.name());
            }
        }
        Ingridient newIngridient = Ingridient.of(null, userId, ingridient.name(), ingridient.protein(), ingridient.fat()
        , ingridient.carbohydrates(), image);

        return ingridientRepository.save(newIngridient);
    }

    @SneakyThrows
    public Ingridient redactIngridient(@Valid IngridientRequest request, Authentication authentication,
                                       @Valid UUID ingridient_id){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Ingridient ingridient = ingridientRepository.findById(ingridient_id).orElseThrow(() -> new NotFoundException("Ingridient with the id not found"));

        if (!ingridient.getUser_id().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        if (ingridientRepository.findByName(request.name()).isPresent()){
            throw new WrongData("This name exists in DB");
        }

        String image = null;
        if (request.image() != null){
            try {
                String name = request.image() + ".png";
                Path filePath = externalFolderPath.resolve(name);

                Resource resource = new UrlResource(filePath.toUri());

                if (resource.exists() || resource.isReadable()) {
                    image = request.image();
                } else {
                    throw new NotFoundException("Could not find or read file: " + request.name());
                }
            } catch (NotFoundException ex){
                throw new RuntimeException("Could not find or read file: " + request.name());
            }
        }

        Ingridient newIngridient = Ingridient.of(null, userId, request.name(), request.protein(), request.fat()
                , request.carbohydrates(), image);

        return ingridientRepository.save(newIngridient);
    }

    @SneakyThrows
    public String deleteIngridient(@Valid UUID ingridient_id, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Ingridient ingridient = ingridientRepository.findById(ingridient_id).orElseThrow(() -> new NotFoundException("Ingridient with the id not found"));

        if (!ingridient.getUser_id().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("You don't have sufficient rights");
        }

        ingridientRepository.deleteById(ingridient_id);
        return "success";
    }

    @SneakyThrows
    public List<Ingridient> getingridients(Authentication authentication){

        List<Ingridient> listIngridients = ingridientRepository.findAll();

        return listIngridients;
    }
}
