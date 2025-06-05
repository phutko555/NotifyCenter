package com.crocobet.notifications.security;

import com.crocobet.notifications.service.userDetails.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{

        security.csrf(csrf -> csrf.disable());
        security.authorizeHttpRequests(auth ->
                auth.requestMatchers("/").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/addressType/**").hasRole("ADMIN")
                        .requestMatchers("/preferenceType/**").hasRole("ADMIN")
                        .requestMatchers("/customer/**").hasRole("ADMIN")
                        .requestMatchers("/api/notifications/**").hasRole("SYSTEM")
                        .requestMatchers("/api/preferences/**").hasRole("SYSTEM")
                        .requestMatchers("/api/address/**").hasRole("SYSTEM")

                        .anyRequest().authenticated()
        ) .httpBasic(Customizer.withDefaults())
                .formLogin(login ->
                login.loginPage("/login").permitAll()
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/admin/dashboard", true)
        );

        return security.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

}
