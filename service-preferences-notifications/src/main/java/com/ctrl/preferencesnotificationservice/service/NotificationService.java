package com.ctrl.preferencesnotificationservice.service;

import com.ctrl.preferencesnotificationservice.model.Notification;
import com.ctrl.preferencesnotificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(String id) {
        return notificationRepository.findById(id).orElseThrow();
    }

    public List<Notification> findByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification insert(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification update(Notification notification) {
        Optional<Notification> currentNotification = notificationRepository.findById(notification.getId());

        if (!currentNotification.isPresent()) {
            return null;
        }

        return notificationRepository.save(notification);
    }

    public void delete(String id) {
        Optional<Notification> notification = notificationRepository.findById(id);

        if (notification.isPresent()) {
            notificationRepository.delete(notification.get());
        }
    }

}
