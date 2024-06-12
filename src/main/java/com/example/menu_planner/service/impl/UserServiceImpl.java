package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.exception.WrongDataLogin;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;

    @SneakyThrows
    public JwtResponse registrationUser(@Valid UserRegistration userRegistration){
        if (userRepository.findByEmail(userRegistration.email()).isPresent()){
            throw new UserAlreadyExistException("User with email: " + userRegistration.email() + " already exist");
        }

        User user = User.of(null, userRegistration.name(), userRegistration.surname(), userRegistration.password(), userRegistration.email(), "ROLE_USER");
        User savedUser = userRepository.save(user);
        String token = tokenUtils.generateToken(savedUser);
        return new JwtResponse(token);
    }

    @SneakyThrows
    public JwtResponse loginUser(@Valid UserLogin request){
        User user = (User) loadUserByUsername(request.email());
        if (!Objects.equals(user.getPassword(), request.password())){
            throw new WrongDataLogin("Incorrect login or password");
        }
        String token = tokenUtils.generateToken(user);
        return new JwtResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new WrongDataLogin("Incorrect login or password")
        );
    }
}
