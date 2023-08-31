package com.ctrl.preferencesnotificationservice.repository;

import com.ctrl.preferencesnotificationservice.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {

}