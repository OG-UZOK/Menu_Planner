package com.example.menu_planner.config;

import com.example.menu_planner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/category/create").authenticated()
                        .requestMatchers("/category/all").authenticated()
                        .requestMatchers("/category").authenticated()
                        .requestMatchers("/user/profile").authenticated()
                        .requestMatchers("/user/profile").authenticated()
                        .requestMatchers("/ingridient/create").authenticated()
                        .requestMatchers("/ingridient/redact").authenticated()
                        .requestMatchers("/ingridient/delete").authenticated()
                        .requestMatchers("/ingridient/all").authenticated()
                        .requestMatchers("/category/create").authenticated()
                        .requestMatchers("/tag/create").authenticated()
                        .requestMatchers("/tag/all").authenticated()
                        .requestMatchers("/dish/all").authenticated()
                        .requestMatchers("/dish").authenticated()
                        .requestMatchers("/dish/create").authenticated()
                        .requestMatchers("/dish/redact").authenticated()
                        .requestMatchers("/dish/delete").authenticated()
                        .requestMatchers("/dishesOnDate/create").authenticated()
                        .requestMatchers("/dishesOnDate").authenticated()
                        .requestMatchers("/dishesOnDate/redact").authenticated()
                        .requestMatchers("/step/image/upload").authenticated()
                        .requestMatchers("/step/image/download/").authenticated()
                        .requestMatchers("/user/logout").authenticated()
                        .requestMatchers("/user/password").authenticated()
                        .requestMatchers("/physiological/create").authenticated()
                        .requestMatchers("/physiological").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService((UserDetailsService) userService);
//        return daoAuthenticationProvider;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
