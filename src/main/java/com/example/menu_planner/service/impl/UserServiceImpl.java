package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;

    @SneakyThrows
    @Override
    public JwtResponse registrationUser(@Valid UserRegistration userRegistration){
        if (userRepository.findByEmail(userRegistration.email()).isPresent()){
            throw new UserAlreadyExistException("User with email:" + userRegistration.email() + "already exist");
        }

        User user = User.of(null, userRegistration.email(), userRegistration.name(), userRegistration.password(), userRegistration.surname());
        User savedUser = userRepository.save(user);
        String token = tokenUtils.generateToken(savedUser);
        return new JwtResponse(token);
    }
}
