package com.example.menu_planner.model.util;

public class JwtAuthenticationDetails {
    private final String token;

    public JwtAuthenticationDetails(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
