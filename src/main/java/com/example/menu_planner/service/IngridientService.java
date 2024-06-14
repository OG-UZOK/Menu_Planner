package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.entity.Ingridient;
import jakarta.validation.Valid;

public interface IngridientService {
    public Ingridient createIngridient(@Valid IngridientRequest ingridient);
}
