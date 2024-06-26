package com.example.menu_planner.handler;

import com.example.menu_planner.exception.*;
import com.example.menu_planner.model.dtoOutput.ExceptionResponse;
import com.example.menu_planner.model.validation.ValidationErrorResponse;
import com.example.menu_planner.model.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

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
    public ExceptionResponse handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WrongData.class)
    public ExceptionResponse handleWrongDataLoginException(WrongData ex) {
        return new ExceptionResponse(ex.getMessage());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = HttpClientErrorException.Unauthorized.class)
    public ExceptionResponse handleUnauthorizedException(HttpClientErrorException.Unauthorized ex){
        return new ExceptionResponse("Not authorized");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ForbiddenException.class)
    public ExceptionResponse handleForbiddenException(ForbiddenException ex){
        return new ExceptionResponse(ex.getMessage());
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ExceptionResponse handleNotFoundException(NotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ExceptionResponse handleUnauthorizedException(UnauthorizedException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = RuntimeException.class)
//    public ExceptionResponse handleRuntimeException(RuntimeException ex) {
//        return new ExceptionResponse(ex.getMessage());
//    }

}
