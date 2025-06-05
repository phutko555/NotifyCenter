package com.crocobet.notifications.service;

import com.crocobet.notifications.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private double calculateRateByStatus(String status) {
        long total = notificationRepository.countAllNotifications();
        if (total == 0) return 0.0;

        long count = notificationRepository.countByStatus(status);
        return (count * 100.0) / total;
    }

    public double getDeliverySuccessRate() {
        return calculateRateByStatus("DELIVERED");
    }

    public double calculateDeliveryFailedRate() {
        return calculateRateByStatus("FAILED");
    }

    public double calculateDeliveryPendingRate() {
        return calculateRateByStatus("PENDING");
    }

}
