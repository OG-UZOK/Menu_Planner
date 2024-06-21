package com.example.menu_planner.service;

import com.example.menu_planner.model.dtoInput.PhysiologicalRequest;
import com.example.menu_planner.model.entity.Physiological;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface PhysiologicalService {
    public Physiological createPhysiological(@Valid PhysiologicalRequest request, Authentication authentication);

    public Physiological getPhysiological(Authentication authentication);
}

