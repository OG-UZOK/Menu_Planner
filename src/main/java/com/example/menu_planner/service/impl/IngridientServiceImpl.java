package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.repository.IngridientRepository;
import com.example.menu_planner.service.IngridientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class IngridientServiceImpl implements IngridientService {
    private final IngridientRepository ingridientRepository;

    @SneakyThrows
    public Ingridient createIngridient(@Valid IngridientRequest ingridient, Authentication authentication){

        if (ingridientRepository.findByName(ingridient.name()).isPresent()){
            throw new WrongData("This name exists in DB");
        }
        Ingridient newIngridient = Ingridient.of(null, ingridient.name(), ingridient.is_liquid(), ingridient.protein(), ingridient.fat()
        , ingridient.carbohydrates());

        return ingridientRepository.save(newIngridient);
    }
}
