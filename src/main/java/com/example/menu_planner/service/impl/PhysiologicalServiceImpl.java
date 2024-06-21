package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.model.dtoInput.PhysiologicalRequest;
import com.example.menu_planner.model.entity.Physiological;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.PhysiologicalRepository;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.PhysiologicalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class PhysiologicalServiceImpl implements PhysiologicalService {
    private final PhysiologicalRepository physiologicalRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;

    @SneakyThrows
    public Physiological createPhysiological(@Valid PhysiologicalRequest request, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);

        double weight = request.weight().doubleValue();
        double height = request.height().doubleValue();

        String daylyActivityString = "Not Found";
        String targetString = "Not Found";

        double bodyMassIndex = weight / ((height / 100) * (height / 100));

        BigDecimal bodyMassIndexRounded = new BigDecimal(bodyMassIndex).setScale(1, RoundingMode.HALF_UP);
        double bodyMassIndexResult = bodyMassIndexRounded.doubleValue();

        Double daylyActivity = 0.0;

        switch (request.dailyActivity()) {
            case (1):
                daylyActivity = 1.2;
                daylyActivityString = "Очень низкая";
                break;
            case (2):
                daylyActivity = 1.375;
                daylyActivityString = "Низкая";
                break;
            case (3):
                daylyActivity = 1.55;
                daylyActivityString = "Средняя";
                break;
            case (4):
                daylyActivity = 1.725;
                daylyActivityString = "Высокая";
                break;
            case (5):
                daylyActivity = 1.9;
                daylyActivityString = "Очень высокая";
                break;
        }

        Double target = 0.0;

        switch (request.target()) {
            case (1):
                target = 0.85;
                targetString = "Сбросить вес";
                break;
            case (2):
                target = 1.0;
                targetString = "Поддерживать вес";
                break;
            case (3):
                target = 1.15;
                targetString = "Набрать вес";
                break;
        }

        Integer totalCalories = 0;

        if (request.gender().equals("Male")) {
            totalCalories = (int) Math.round((10 * request.weight() + 6.25 * request.height() - 5 * request.age() + 5) * daylyActivity * target);
        } else {
            totalCalories = (int) Math.round((10 * request.weight() + 6.25 * request.height() - 5 * request.age() - 161) * daylyActivity * target);
        }

        // Используем NumberFormat для форматирования чисел с точкой как десятичным разделителем
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMaximumFractionDigits(2);
        format.setGroupingUsed(false);

        double proteinCaloriesDouble = Double.parseDouble(format.format(totalCalories * 0.25 / 4));
        double fatCaloriesDouble = Double.parseDouble(format.format(totalCalories * 0.22 / 9));
        double carbCaloriesDouble = Double.parseDouble(format.format(totalCalories * 0.53 / 4));

        Physiological physiological = Physiological.of(null, userId, request.gender(), request.age(), request.height(),
                request.weight(), daylyActivityString, targetString, bodyMassIndexResult, totalCalories, proteinCaloriesDouble,
                fatCaloriesDouble, carbCaloriesDouble);

        physiologicalRepository.deleteAllByUser_id(userId);

        return physiologicalRepository.save(physiological);
    }

    @SneakyThrows
    public Physiological getPhysiological(Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        Physiological physiological = physiologicalRepository.findByUserId(userId).orElseThrow(() ->
                new NotFoundException("physiological with this ID does not exist"));

        return physiological;
    }
}
