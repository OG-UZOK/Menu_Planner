package com.example.menu_planner.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class WrongDataLogin extends UsernameNotFoundException
{

    public WrongDataLogin(String message) {
        super(message);
    }
}
