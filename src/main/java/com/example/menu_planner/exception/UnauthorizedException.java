package com.example.menu_planner.exception;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
