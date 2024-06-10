package com.example.menu_planner.handler;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.model.validation.ValidationErrorResponse;
import com.example.menu_planner.model.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.apache.catalina.connector.Response;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return "An error occurred: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return "An error occurred: " + ex.getMessage();
    }
}