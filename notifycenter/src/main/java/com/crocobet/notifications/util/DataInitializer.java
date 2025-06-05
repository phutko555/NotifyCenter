package com.crocobet.notifications.util;

import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.enums.Role;

import com.crocobet.notifications.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("notifier").isEmpty()) {
            User systemUser = new User();
            systemUser.setUsername("notifier");
            systemUser.setPassword(passwordEncoder.encode("system2004"));
            systemUser.setRole(Role.SYSTEM);
            userRepository.save(systemUser);
        }
    }
}

