package com.crocobet.notifications.repository;

import com.crocobet.notifications.model.notification.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_UserId(Long userId);

    List<Notification> findByStatus(String status);

    List<Notification> findByChannel(String channel);

    long countByStatus(String status);

    @Query("SELECT COUNT(n) FROM Notification n")
    long countAllNotifications();

}
