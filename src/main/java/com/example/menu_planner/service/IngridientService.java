package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.IngridientRequest;
import com.example.menu_planner.model.entity.Ingridient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface IngridientService {
    public Ingridient createIngridient(@Valid IngridientRequest ingridient, Authentication authentication, String token);

    public Ingridient redactIngridient(@Valid IngridientRequest request, Authentication authentication,
                                       @Valid UUID ingridient_id, String token);

    public String deleteIngridient(@Valid UUID ingridient_id, Authentication authentication, String token);

    public List<Ingridient> getingridients(Authentication authentication, String token);

    public Ingridient getIngridientFindByName(String name, Authentication authentication, String token);

}

