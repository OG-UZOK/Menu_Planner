package com.example.menu_planner.model.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
