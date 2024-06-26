package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.UnauthorizedException;
import com.example.menu_planner.exception.UserAlreadyExistException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.Password;
import com.example.menu_planner.model.dtoInput.UserLogin;
import com.example.menu_planner.model.dtoInput.UserRegistration;
import com.example.menu_planner.model.dtoOutput.JwtResponse;
import com.example.menu_planner.model.dtoOutput.UserProfileResponse;
import com.example.menu_planner.model.entity.DeletedTokens;
import com.example.menu_planner.model.entity.User;
import com.example.menu_planner.model.util.JwtAuthenticationDetails;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.DeletedTokensRepository;
import com.example.menu_planner.repository.UserRepository;
import com.example.menu_planner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final DeletedTokensRepository deletedTokensRepository;



    @SneakyThrows
    public JwtResponse registrationUser(@Valid UserRegistration userRegistration){
        if (userRepository.findByEmail(userRegistration.email()).isPresent()){
            throw new UserAlreadyExistException("User with email: " + userRegistration.email() + " already exist");
        }
        String encodedPassword = passwordEncoder.encode(userRegistration.password());
        System.out.println(encodedPassword);
        User user = User.of(null, userRegistration.name(), userRegistration.surname(), encodedPassword, userRegistration.email(), "ROLE_USER");
        User savedUser = userRepository.save(user);
        String token = tokenUtils.generateToken(savedUser);
        return new JwtResponse(token);
    }

    @SneakyThrows
    public JwtResponse loginUser(@Valid UserLogin request){
        User user = (User) loadUserByUsername(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new WrongData("Incorrect login or password");
        }

        String token = tokenUtils.generateToken(user);
        return new JwtResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new WrongData("Incorrect login or password")
        );
    }

    @SneakyThrows
    public UserProfileResponse getProfile(Authentication authentication, String token) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        return new UserProfileResponse(user.getEmail(), user.getName(), user.getSurname());
    }

    @SneakyThrows
    public UserProfileResponse redactProfile(@Valid UserProfileResponse userProfileResponse, Authentication authentication, String token) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }

        Optional<User> existingUser = userRepository.findByEmail(userProfileResponse.email());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new UserAlreadyExistException("User with email: " + userProfileResponse.email() + " already exist");
        }

        user.setEmail(userProfileResponse.email());
        user.setName(userProfileResponse.name());
        user.setSurname(userProfileResponse.surname());
        userRepository.save(user);
        return userProfileResponse;
    }

    @SneakyThrows
    public Password changePassword(@Valid Password password, Authentication authentication, String token) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        user.setPassword(passwordEncoder.encode(password.password()));
        return password;
    }

    @SneakyThrows
    public String logout(Authentication authentication , String token) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        DeletedTokens deletedToken = DeletedTokens.of(token);
        deletedTokensRepository.save(deletedToken);
        return "success";

    }


}

