package com.ctrl.preferencesnotificationservice.service;

import com.ctrl.preferencesnotificationservice.model.Notification;
import com.ctrl.preferencesnotificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

}
