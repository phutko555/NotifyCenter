package com.crocobet.notifications.controller;

import com.crocobet.notifications.dto.notification.NotificationStatusDTO;

import com.crocobet.notifications.model.User;

import com.crocobet.notifications.model.notification.Notification;

import com.crocobet.notifications.repository.NotificationRepository;

import com.crocobet.notifications.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/track")
    @Transactional
    public ResponseEntity<String> trackNotification(@RequestBody NotificationStatusDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean optedIn = user.getPreference().stream()
                .anyMatch(p ->
                        p.getPreferenceType().getPreferenceTypeName().equalsIgnoreCase(dto.getChannel()) &&
                                Boolean.TRUE.equals(p.getOptedIn())
                );

        if (!optedIn) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("მომხმარებელს არ სურს რომ მიიღოს შეტყობინება : " + dto.getChannel() +"'ის საშუალებით");
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setStatus(dto.getStatus());
        notification.setChannel(dto.getChannel());
        notification.setSentAt(dto.getSentAt() != null ? dto.getSentAt() : LocalDateTime.now());

        notificationRepository.save(notification);

        return ResponseEntity.ok("შეტყობინება წარმატებით შეინახა");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationRepository.findByUser_UserId(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(notificationRepository.findByStatus(status.toUpperCase()));
    }

    @GetMapping("/channel/{channel}")
    public ResponseEntity<List<Notification>> getNotificationsByChannel(@PathVariable String channel) {
        return ResponseEntity.ok(notificationRepository.findByChannel(channel.toUpperCase()));
    }
}
